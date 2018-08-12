package win.hupubao.core.filters;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import win.hupubao.common.utils.LoggerUtils;
import win.hupubao.common.utils.StringUtils;
import win.hupubao.constants.Constants;
import win.hupubao.core.properties.AccessOriginProperties;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author W.feihong
 * @date 2018-07-27
 * 跨域过滤器
 */

@Component
@Order(1)
@WebFilter(filterName = "accessOriginFilter", urlPatterns = "/*")
public class AccessOriginFilter implements Filter {

    @Autowired
    private AccessOriginProperties accessOriginProperties;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;


        //跨域
        String origin = req.getHeader("Origin");
        LoggerUtils.info("[Request Origin][{}],Allows:{}", origin, JSON.toJSONString(accessOriginProperties.getAllowOrigins()));

        if (StringUtils.isNotBlank(origin)
                && accessOriginProperties.getAllowOrigins().size() != 0) {
            String originAllowed = accessOriginProperties.getAllowOrigins().contains(origin) ? origin : "";
            res.addHeader("Access-Control-Allow-Origin", originAllowed);
            res.addHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
            res.addHeader("Access-Control-Allow-Headers", "X-Requested-With,Content-Type");
            res.addHeader("Access-Control-Allow-Credentials", "true");

        }

        chain.doFilter(request, response);


    }

    @Override
    public void destroy() {

    }
}