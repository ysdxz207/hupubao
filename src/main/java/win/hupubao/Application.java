package win.hupubao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.spring.annotation.MapperScan;
import win.hupubao.beans.RequestBean;
import win.hupubao.beans.ResponseBean;
import win.hupubao.common.error.SystemError;
import win.hupubao.common.utils.LoggerUtils;
import win.hupubao.common.utils.StringUtils;
import win.hupubao.utils.SpringContextUtils;
import win.hupubao.utils.mybatis.MyMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.stream.Collectors;

/**
 * @author Moses
 * @copyright Copyright by www.lamic.cn
 * 注解@EnableTransactionManagement开启事务等同于xml:<tx:annotation-driven/>
 */
@SpringBootApplication
@RestController
@EnableTransactionManagement
@MapperScan(basePackages = {"win.hupubao.mapper"}, markerInterface = MyMapper.class)
public class Application {

    @RequestMapping("/")
    private Object index(HttpServletRequest request,
                         HttpServletResponse response) {
        RequestBean requestBean;
        ResponseBean responseBean = new ResponseBean();
        JSONObject params = new JSONObject();

        try {
            String methodType = request.getMethod();
            HttpMethod httpMethod = HttpMethod.valueOf(methodType);

            switch (httpMethod) {
                case GET:
                    Enumeration<String> parameterNames = request.getParameterNames();


                    while (parameterNames.hasMoreElements()) {
                        String parameterName = parameterNames.nextElement();
                        String parameterValue = request.getParameter(parameterName);
                        params.put(parameterName, parameterValue);
                    }
                    break;
                case POST:

                    String jsonString = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
                    params = JSON.parseObject(jsonString);
                    break;
                    default:
                        return responseBean.errorMessage("Unsupported request method.");
            }

            requestBean = JSON.toJavaObject(params, RequestBean.class);

            String[] service = requestBean.getService().split("\\.");
            String className = StringUtils.firstToUpperCase(service[0]) + "Service";
            String methodName = service[1];

            Class<?> cla = Class.forName(new StringBuilder("win.hupubao.service.")
                    .append(className).toString());
            Object clazz = SpringContextUtils.getBean(cla);
            Method method = clazz.getClass().getDeclaredMethod(methodName,
                    HttpServletRequest.class,
                    HttpServletResponse.class, RequestBean.class);
            try {
                return method.invoke(clazz, request, response, requestBean);
            } catch (Exception e) {
                return responseBean.error(e);
            }
        } catch (Exception e) {
            responseBean.error(SystemError.PARAMETER_ERROR);
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
