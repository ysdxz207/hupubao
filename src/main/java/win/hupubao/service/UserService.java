package win.hupubao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import win.hupubao.beans.RequestBean;
import win.hupubao.beans.ResponseBean;
import win.hupubao.domain.User;
import win.hupubao.persistence.UserMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author W.feihong
 * @date 2018-07-25
 * 用户服务
 */

@Service
public class UserService {


    @Autowired
    private UserMapper userMapper;

    public ResponseBean login(HttpServletRequest request,
                              HttpServletResponse response,
                              RequestBean requestBean) {
        ResponseBean responseBean = new ResponseBean();

        User user = new User();
        user.setId("20151106");
        user = userMapper.selectOne(user);

        responseBean.success(user);
        return responseBean;
    }
}
