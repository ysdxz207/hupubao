package win.hupubao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import win.hupubao.beans.RequestBean;
import win.hupubao.beans.ResponseBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@SpringBootApplication
@RestController
public class HupubaoApplication {

	@RequestMapping("/")
	private ResponseBean index(HttpServletRequest request,
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

		} catch (Exception e) {
			
		}

		return responseBean;
	}

	public static void main(String[] args) {
		SpringApplication.run(HupubaoApplication.class, args);
	}
}
