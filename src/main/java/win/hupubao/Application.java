package win.hupubao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.spring.annotation.MapperScan;
import win.hupubao.beans.sys.RequestBean;
import win.hupubao.beans.sys.ResponseBean;
import win.hupubao.common.error.SystemError;
import win.hupubao.common.error.Throws;
import win.hupubao.common.utils.LoggerUtils;
import win.hupubao.core.annotation.ServiceInfo;
import win.hupubao.service.UserService;
import win.hupubao.utils.ApplicationContextUtils;
import win.hupubao.utils.mybatis.MyMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.stream.Collectors;

/**
 * @author W.feihong
 * 注解@EnableTransactionManagement开启事务等同于xml:<tx:annotation-driven/>
 */
@SpringBootApplication
@RestController
@EnableTransactionManagement
@EnableAutoConfiguration
@MapperScan(basePackages = {"win.hupubao.mapper"}, markerInterface = MyMapper.class)
public class Application {


    @Autowired
    private UserService userService;


    @RequestMapping("/")
    private Object index(HttpServletRequest request,
                         HttpServletResponse response) {
        RequestBean requestBean = new RequestBean();
        ResponseBean responseBean = new ResponseBean();
        JSONObject params = new JSONObject();

        try {
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

            String beanName = null;
            String methodName = null;
            try {
                requestBean = JSON.toJavaObject(params, RequestBean.class);
                int index = requestBean.getService().indexOf(".");
                beanName = requestBean.getService().substring(0, index);
                methodName = requestBean.getService().substring(index + 1);
            } catch (Exception e) {
                Throws.throwError(SystemError.PARAMETER_ERROR);
            }


            //登录及权限
            userService.vilidateAuth(request, response, requestBean);

            Object action = ApplicationContextUtils.getBean(beanName);

            Method[] methods = action.getClass().getDeclaredMethods();
            Method method = null;
            for (Method m :
                    methods) {
                ServiceInfo serviceName = m.getAnnotation(ServiceInfo.class);
                if (serviceName != null
                        && serviceName.value().equals(methodName)) {
                    method = m;
                    break;
                }
            }

            if (method == null) {
                Throws.throwError(SystemError.PARAMETER_ERROR);
            }

            try {
                return method.invoke(action, request, response, requestBean);
            } catch (Exception e) {
                LoggerUtils.error("[请求异常]", e);
                return responseBean.error(e);
            }
        } catch (Exception e) {
            responseBean.error(e);
        }

        return responseBean;
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
