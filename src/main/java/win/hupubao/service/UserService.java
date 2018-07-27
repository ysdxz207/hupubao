package win.hupubao.service;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;
import win.hupubao.beans.RequestBean;
import win.hupubao.beans.ResponseBean;
import win.hupubao.common.annotations.LogReqResArgs;
import win.hupubao.common.error.Throws;
import win.hupubao.common.exception.ThrowsBisinessException;
import win.hupubao.common.utils.Md5Utils;
import win.hupubao.common.utils.StringUtils;
import win.hupubao.common.utils.rsa.RSA;
import win.hupubao.constants.Constants;
import win.hupubao.core.configuration.AuthConfiguration;
import win.hupubao.core.errors.LoginError;
import win.hupubao.domain.User;
import win.hupubao.domain.UserSecurity;
import win.hupubao.mapper.UserMapper;
import win.hupubao.mapper.UserSecurityMapper;
import win.hupubao.utils.CookieUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author W.feihong
 * @date 2018-07-25
 * 用户服务
 */

@Service
@EnableAutoConfiguration
public class UserService extends BaseService{

    @Autowired
    private AuthConfiguration authConfiguration;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserSecurityMapper userSecurityMapper;


    @LogReqResArgs
    public ResponseBean login(HttpServletRequest request,
                              HttpServletResponse response,
                              RequestBean requestBean) {
        ResponseBean responseBean = new ResponseBean(requestBean.getService());

        try {

            User user = getEntity(requestBean, User.class, true);

            //校验验证码
            if (!user.getCaptcha().equalsIgnoreCase((String) request.getSession().getAttribute(Constants.CAPTCHA_SESSION_KEY))) {
                Throws.throwError(LoginError.WRONG_CAPTCHA_ERROR);
            }

            user.setPassword(Md5Utils.md5(user.getPassword() + Constants.PASSWORD_MD5_SALT));
            User userLogin = userMapper.selectOne(user);

            if (userLogin == null) {
                responseBean.error(LoginError.WRONG_USERNAME_OR_PASSWORD_ERROR);
            } else {
                responseBean.success(userLogin);
                //登录成功
                rememberMe(response, user);
                userLogin.setPassword(null);
                request.getSession().setAttribute(Constants.SESSION_USER_KEY, userLogin);
            }
        } catch (Exception e) {
            responseBean.error(e);
            e.printStackTrace();
        }
        return responseBean;
    }

    private void rememberMe(HttpServletResponse response,
                            User user) throws Exception{

        //自动登录
        boolean rememberMe = StringUtils.isNotBlank(user.getRememberMe())
                && ("on".equalsIgnoreCase(user.getRememberMe()) || Boolean.valueOf(user.getRememberMe()));


        if (!rememberMe) {
            return;
        }

        //每次自动登录都生成一对私钥公钥
        RSA rsa = RSA.getInstance();
        RSA.RSAKey rsaKey = rsa.generateRSAKey();

        UserSecurity userSecurity = new UserSecurity();
        userSecurity.setUserId(user.getId());
        userSecurity = userSecurityMapper.selectOne(userSecurity);

        if (userSecurity == null) {
            userSecurity = new UserSecurity();
            userSecurity.setUserId(user.getId());
            userSecurity.setCreateTime(System.currentTimeMillis());
        }
        userSecurity.setPrivateKey(rsaKey.getPrivateKey());
        userSecurity.setPublicKey(rsaKey.getPublicKey());

        if (userSecurity.getId() == null) {
            userSecurityMapper.insertSelective(userSecurity);
        } else {
            userSecurityMapper.updateByPrimaryKey(userSecurity);
        }

        String cookieStr = JSON.toJSONString(user);
        try {
            cookieStr = rsa.rsaKey(rsaKey).encryptByPrivateKey(cookieStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        cookieStr = user.getId() + "-" + cookieStr;
        Cookie cookie = new Cookie(Constants.COOKIE_LOGIN_KEY_FBLOG,
                cookieStr);
        cookie.setMaxAge(24 * 3600 * 365);
        response.addCookie(cookie);

    }

    public boolean cookieLogin(HttpServletRequest request,
                            HttpServletResponse response,
                            RequestBean requestBean) throws Exception {

        String str = CookieUtils.getCookie(request, Constants.COOKIE_LOGIN_KEY_FBLOG);

        if (StringUtils.isNotBlank(str)) {
            String [] arr = str.split("_");
            String userId = arr[0];
            str = arr[1];

            UserSecurity userSecurity = new UserSecurity();
            userSecurity.setUserId(userId);
            userSecurity = userSecurityMapper.selectOne(userSecurity);

            if (userSecurity == null) {
                return false;
            }

            RSA.RSAKey rsaKey = new RSA.RSAKey(userSecurity.getPrivateKey(), userSecurity.getPublicKey());

            str = RSA.getInstance().rsaKey(rsaKey).decryptByPublicKey(str);
            User user = JSON.toJavaObject(JSON.parseObject(str), User.class);

            if (user != null) {
                String sessionCaptcha = (String) request.getSession().getAttribute(Constants.CAPTCHA_SESSION_KEY);
                user.setCaptcha(sessionCaptcha);
                user.setSessionCaptcha(sessionCaptcha);
                return "SUCCESS".equals(login(request, response, requestBean).getErrorCode());
            }
        }

        return false;
    }


    public void vilidateAuth(HttpServletRequest request,
                             HttpServletResponse response,
                             RequestBean requestBean) throws Exception {
        String service = requestBean.getService();
        //非登录排除且未登录
        if (!authConfiguration.getExcludes().contains(service)
                && StringUtils.isEmpty(request.getSession().getAttribute(Constants.SESSION_USER_KEY))
                && cookieLogin(request, response, requestBean)) {
            Throws.throwError(LoginError.NO_AUTH_ERROR);
        }

        //权限


    }
}
