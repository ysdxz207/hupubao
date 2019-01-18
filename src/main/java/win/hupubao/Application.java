package win.hupubao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.spring.annotation.MapperScan;
import win.hupubao.beans.biz.AfuTypeBean;
import win.hupubao.beans.sys.PageBean;
import win.hupubao.beans.sys.RequestBean;
import win.hupubao.beans.sys.ResponseBean;
import win.hupubao.common.error.SystemError;
import win.hupubao.common.error.Throws;
import win.hupubao.common.utils.LoggerUtils;
import win.hupubao.common.utils.Md5Utils;
import win.hupubao.common.utils.StringUtils;
import win.hupubao.common.utils.rsa.RSA;
import win.hupubao.core.annotation.NeedVerifySign;
import win.hupubao.core.annotation.ServiceInfo;
import win.hupubao.enums.SignType;
import win.hupubao.service.AfuTypeService;
import win.hupubao.service.UserService;
import win.hupubao.utils.ApplicationContextUtils;
import win.hupubao.utils.mybatis.MyMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ysdxz207
 * 注解@EnableTransactionManagement开启事务等同于xml:<tx:annotation-driven/>
 */
@SpringBootApplication
@RestController
@EnableTransactionManagement
@MapperScan(basePackages = {"win.hupubao.mapper"}, markerInterface = MyMapper.class)
public class Application {

    private static final int MAX_RANDOM_STRING_LENGTH = 32;

    @Autowired
    private UserService userService;
    @Autowired
    private AfuTypeService afuTypeService;


    @RequestMapping("/")
    private Object index(HttpServletRequest request,
                         HttpServletResponse response) {
        RequestBean requestBean = new RequestBean();
        ResponseBean responseBean = new ResponseBean();

        try {
            requestBean = parseParameters(request);

            String beanName = null;
            String methodName = null;
            try {
                int index = requestBean.getService().indexOf(".");
                beanName = requestBean.getService().substring(0, index);
                methodName = requestBean.getService().substring(index + 1);
            } catch (Exception e) {
                Throws.throwError(SystemError.PARAMETER_ERROR);
            }


            //登录及权限
            userService.vilidateAuth(request, response, requestBean);

            Object action = ApplicationContextUtils.getBean(beanName);

            if (action == null) {
                Throws.throwError(SystemError.PARAMETER_ERROR, "Unknown action .");
            }

            Method[] methods = action.getClass().getDeclaredMethods();
            Method method = null;
            for (Method m :
                    methods) {
                // 因为有aop，cglib动态代理类的注解无法直接获取，通过spring的工具获取。
                ServiceInfo serviceName = AnnotationUtils.findAnnotation(m, ServiceInfo.class);
                if (serviceName != null && serviceName.value().equals(methodName)) {
                    method = m;
                    break;
                }
            }

            if (method == null) {
                Throws.throwError(SystemError.PARAMETER_ERROR, "Unknown method .");
            }

            AfuTypeBean afuTypeBean = afuTypeService.selectByPrimaryKey(requestBean.getAfuType());
            if (afuTypeBean == null) {
                Throws.throwError(SystemError.PARAMETER_ERROR, "阿福类别不存在");
            }

            requestBean.setAfuTypeBean(afuTypeBean);

            // 验签
            if (method.isAnnotationPresent(NeedVerifySign.class)) {
                verifySign(afuTypeBean, requestBean);
            }

            responseBean = (ResponseBean) method.invoke(action, request, response, requestBean);
            responseBean.setRandomString(requestBean.getRandomString());
            responseBean.setService(requestBean.getService());
            responseBean.setSignType(requestBean.getSignType());
            responseBean.setSign(makeSign(afuTypeBean, responseBean));
            return responseBean;
        } catch (Exception e) {
            LoggerUtils.error("请求接口[{}]异常", requestBean.getService(), e);
            responseBean.error(e);
        }

        return responseBean;
    }

    /**
     * 参数解析
     *
     * @param request
     * @return
     */
    private RequestBean parseParameters(HttpServletRequest request) throws Exception {
        JSONObject params = new JSONObject();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            String parameterValue = request.getParameter(parameterName);
            params.put(parameterName, parameterValue);
        }

        if (params.isEmpty()) {
            String jsonString = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            params = JSON.parseObject(jsonString);
        }

        RequestBean requestBean = JSON.toJavaObject(params, RequestBean.class);
        verifyParameter(requestBean);
        return requestBean;
    }

    /**
     * 检查参数是否有效
     *
     * @param requestBean
     */
    private void verifyParameter(RequestBean requestBean) {

        if (requestBean == null) {
            Throws.throwError(SystemError.PARAMETER_ERROR);
        }

        if (StringUtils.isBlank(requestBean.getService())) {
            Throws.throwError(SystemError.PARAMETER_ERROR, "Need Argument [service].");
        }

        if (StringUtils.isBlank(requestBean.getAfuType())) {
            Throws.throwError(SystemError.PARAMETER_ERROR, "Need Argument [afuType].");
        }


    }

    /**
     * 签名
     *
     * @param afuTypeBean
     * @param result
     * @return
     */
    private String makeSign(AfuTypeBean afuTypeBean, ResponseBean result) {
        SignType signType = SignType.getSignType(result.getSignType());

        Map<String, String> params = new HashMap<>(6);
        params.put("signType", result.getSignType());
        params.put("service", result.getService());
        if (result instanceof PageBean) {
            params.put("list", JSON.toJSONString(((PageBean) result).getList()));
        } else {
            params.put("data", JSON.toJSONString(result.getData()));
        }

        String sign = null;
        switch (signType) {
            case MD5:
                sign = Md5Utils.sign(afuTypeBean.getMd5Key(), params);
                break;
            case RSA:
            case RSA2:
                sign = RSA.getInstance().sign(afuTypeBean.getPrivateKey(), params, RSA.SignType.RSA);
                break;
            default:
                Throws.throwError(SystemError.PARAMETER_ERROR, "不支持的签名类型");
        }

        return sign;
    }

    /**
     * 验签
     *
     * @param afuTypeBean
     * @param requestBean
     */
    private void verifySign(AfuTypeBean afuTypeBean,
                            RequestBean requestBean) {


        if (StringUtils.isBlank(requestBean.getSign())) {
            Throws.throwError(SystemError.PARAMETER_ERROR, "Need Argument [sign].");
        }

        if (StringUtils.isBlank(requestBean.getSignType())) {
            Throws.throwError(SystemError.PARAMETER_ERROR, "Need Argument [signType].");
        }

        if (StringUtils.isBlank(requestBean.getRandomString())) {
            Throws.throwError(SystemError.PARAMETER_ERROR, "Need Argument [randomString].");
        }

        if (requestBean.getRandomString().length() > MAX_RANDOM_STRING_LENGTH) {
            Throws.throwError(SystemError.PARAMETER_ERROR, "Argument [randomString] length should less than 32.");
        }


        Map<String, String> params = new HashMap<>(6);
        params.put("signType", requestBean.getSignType());
        params.put("service", requestBean.getService());
        params.put("afuType", requestBean.getAfuType());
        params.put("randomString", requestBean.getRandomString());
        params.put("bizContent", requestBean.getBizContent());


        boolean verify = false;
        SignType signType = SignType.getSignType(requestBean.getSignType());

        switch (signType) {
            case MD5:
                verify = Md5Utils.verify(afuTypeBean.getMd5Key(), requestBean.getSign(), params);
                break;
            case RSA:
            case RSA2:
                RSA.RSAKey rsaKey = new RSA.RSAKey(afuTypeBean.getPrivateKey(), afuTypeBean.getPublicKey());
                verify = RSA.getInstance().rsaKey(rsaKey)
                        .verify(params, requestBean.getSign(), RSA.SignType.valueOf(requestBean.getSignType()));
                break;
            default:
                Throws.throwError(SystemError.PARAMETER_ERROR, "不支持的签名类型");

        }

        if (!verify) {
            Throws.throwError(SystemError.SIGN_ERROR);
        }
    }

    @ExceptionHandler({RuntimeException.class})
    @ResponseStatus(HttpStatus.OK)
    private ResponseBean exceptionHandler(RuntimeException e) {
        LoggerUtils.error(e);
        return new ResponseBean();
    }

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }
}
