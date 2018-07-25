package win.hupubao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.spring.annotation.MapperScan;
import win.hupubao.beans.RequestBean;
import win.hupubao.beans.ResponseBean;
import win.hupubao.common.error.SystemError;
import win.hupubao.common.http.Page;
import win.hupubao.common.utils.StringUtils;
import win.hupubao.utils.SpringContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Moses
 * @copyright Copyright by www.lamic.cn
 * 注解@EnableTransactionManagement开启事务等同于xml:<tx:annotation-driven/>
 */
@SpringBootApplication
@RestController
@EnableTransactionManagement
@MapperScan(basePackages = {"win.hupubao.persistence.*"}, markerInterface = Mapper.class)
public class Application {

    @RequestMapping("/")
    private Object index(HttpServletRequest request,
                         HttpServletResponse response) {
        RequestBean requestBean;
        ResponseBean responseBean = new ResponseBean();
        try {
            Enumeration<String> parameterNames = request.getParameterNames();

            JSONObject params = new JSONObject();

            while (parameterNames.hasMoreElements()) {
                String parameterName = parameterNames.nextElement();
                String parameterValue = request.getParameter(parameterName);
                params.put(parameterName, parameterValue);
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

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
