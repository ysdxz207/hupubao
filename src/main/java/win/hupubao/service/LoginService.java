package win.hupubao.service;

import org.springframework.stereotype.Service;
import win.hupubao.beans.ResponseBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class LoginService {

    public ResponseBean login(HttpServletRequest request,
                              HttpServletResponse response) {
        ResponseBean responseBean = new ResponseBean();

        responseBean.setData("haha");

        return responseBean;
    }
}
