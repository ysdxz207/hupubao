package win.hupubao.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import win.hupubao.beans.RequestBean;
import win.hupubao.beans.ResponseBean;
import win.hupubao.common.annotations.LogReqResArgs;
import win.hupubao.common.error.Throws;
import win.hupubao.common.utils.DesUtils;
import win.hupubao.common.utils.LoggerUtils;
import win.hupubao.common.utils.Md5Utils;
import win.hupubao.common.utils.StringUtils;
import win.hupubao.constants.Constants;
import win.hupubao.core.errors.LoginError;
import win.hupubao.domain.User;
import win.hupubao.mapper.UserMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author W.feihong
 * @date 2018-07-25
 * 用户服务
 */

@Service
public class UserService extends BaseService{


    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserMapper userMapper;


    @LogReqResArgs
    public ResponseBean login(HttpServletRequest request,
                              HttpServletResponse response,
                              RequestBean requestBean) {
        ResponseBean responseBean = new ResponseBean(requestBean.getService());

        logger.info("嘿嘿日志");
        logger.error("嘿嘿日志");
        try {

            User user = getEntity(requestBean, User.class, true);

            //校验验证码
            if (!user.getCaptcha().equalsIgnoreCase((String) request.getSession().getAttribute(Constants.CAPTCHA_SESSION_KEY))) {
                Throws.throwError(LoginError.WRONG_CAPTCHA_ERROR);
            }

            user.setPassword(Md5Utils.md5(user.getPassword() + Constants.PASSWORD_MD5_SALT));
            user = userMapper.selectOne(user);

            if (user == null) {
                responseBean.error(LoginError.WRONG_USERNAME_OR_PASSWORD_ERROR);
            } else {
                responseBean.success(user);
                //登录成功
//                rememberMe()
                user.setPassword(null);
                request.getSession().setAttribute(Constants.SESSION_USER_KEY, user);
            }
        } catch (Exception e) {
            responseBean.error(e);
            e.printStackTrace();
        }
        return responseBean;
    }

    public User cookieLogin(HttpServletRequest request,
                            HttpServletResponse response,
                            RequestBean requestBean,
                            Map<String, String> cookies,
                            String sessionCaptcha) throws Exception {

        User user = null;
        String str = cookies.get(Constants.COOKIE_LOGIN_KEY_FBLOG);

        if (StringUtils.isNotBlank(str)) {
            user = new User();
            String[] strArr = DesUtils.decrypt(str, Constants.DES_KEY).split("_");
            user.setUsername(strArr[0]);
            user.setPassword(strArr[1]);
            user.setCaptcha(sessionCaptcha);
            user.setSessionCaptcha(sessionCaptcha);
//            user = login(request, response, requestBean);
        }


        return user;
    }
}
