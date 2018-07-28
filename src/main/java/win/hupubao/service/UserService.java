package win.hupubao.service;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;
import win.hupubao.beans.biz.UserBean;
import win.hupubao.beans.sys.RequestBean;
import win.hupubao.common.error.Throws;
import win.hupubao.common.utils.DesUtils;
import win.hupubao.common.utils.ListUtils;
import win.hupubao.common.utils.Md5Utils;
import win.hupubao.common.utils.StringUtils;
import win.hupubao.constants.Constants;
import win.hupubao.core.annotation.Logical;
import win.hupubao.core.properties.AuthProperties;
import win.hupubao.core.errors.LoginError;
import win.hupubao.domain.User;
import win.hupubao.domain.UserSecurity;
import win.hupubao.mapper.PermissionMapper;
import win.hupubao.mapper.UserMapper;
import win.hupubao.mapper.UserSecurityMapper;
import win.hupubao.utils.CookieUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * @author W.feihong
 * @date 2018-07-25
 * 用户服务
 */

@Service
@EnableAutoConfiguration
public class UserService {

    @Autowired
    private AuthProperties authConfiguration;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserSecurityMapper userSecurityMapper;
    @Autowired
    private PermissionMapper permissionMapper;

    public UserBean login(UserBean userBean) {

        //参数校验
        if (StringUtils.isEmpty(userBean.getUsername())) {
            Throws.throwError(LoginError.NO_USERNAME_ERROR);
        }

        if (StringUtils.isEmpty(userBean.getPassword())) {
            Throws.throwError(LoginError.NO_PASSWORD_ERROR);
        }

        if (StringUtils.isEmpty(userBean.getCaptcha())) {
            Throws.throwError(LoginError.NO_CAPTCHA_ERROR);
        }

        if (!userBean.getCaptcha().equalsIgnoreCase(userBean.getSessionCaptcha())) {
//                Throws.throwError(LoginError.WRONG_CAPTCHA_ERROR);
        }

        userBean.setPassword(Md5Utils.md5(userBean.getPassword() + Constants.PASSWORD_MD5_SALT));
        User userLogin = userMapper.selectOne(userBean);

        if (userLogin == null) {
            Throws.throwError(LoginError.WRONG_USERNAME_OR_PASSWORD_ERROR);
        }

        BeanUtils.copyProperties(userLogin, userBean);
        return userBean;
    }


    public User selectOne(User user) {
        return userMapper.selectOne(user);
    }


    public void rememberMe(HttpServletResponse response,
                            UserBean userBean) throws Exception {

        //自动登录
        boolean rememberMe = StringUtils.isNotBlank(userBean.getRememberMe())
                && ("on".equalsIgnoreCase(userBean.getRememberMe()) || Boolean.valueOf(userBean.getRememberMe()));


        if (!rememberMe) {
            return;
        }

        //每次自动登录都生成des加密key

        UserSecurity userSecurity = new UserSecurity();
        userSecurity.setUserId(userBean.getId());
        userSecurity = userSecurityMapper.selectOne(userSecurity);

        if (userSecurity == null) {
            userSecurity = new UserSecurity();
            userSecurity.setUserId(userBean.getId());
            userSecurity.setCreateTime(System.currentTimeMillis());
        }
        userSecurity.setDesKey(RandomStringUtils.randomAlphanumeric(10));

        if (userSecurity.getId() == null) {
            userSecurityMapper.insertSelective(userSecurity);
        } else {
            userSecurityMapper.updateByPrimaryKey(userSecurity);
        }

        User userCookie = new User();
        userCookie.setUsername(userBean.getUsername());
        userCookie.setPassword(userBean.getPassword());
        String cookieStr = JSON.toJSONString(userCookie);
        cookieStr = DesUtils.encrypt(cookieStr, userSecurity.getDesKey());
        cookieStr = userBean.getId() + "_" + cookieStr;
        CookieUtils.addCookie(response, Constants.COOKIE_LOGIN_KEY_FBLOG, cookieStr);
    }

    public boolean cookieLogin(HttpServletRequest request,
                               HttpServletResponse response) {

        boolean loginSuccess = false;

        try {
            String str = CookieUtils.getCookie(request, Constants.COOKIE_LOGIN_KEY_FBLOG);

            if (StringUtils.isNotBlank(str)) {
                String[] arr = str.split("_");
                String userId = arr[0];
                str = arr[1];

                UserSecurity userSecurity = new UserSecurity();
                userSecurity.setUserId(userId);
                userSecurity = userSecurityMapper.selectOne(userSecurity);

                if (userSecurity == null) {
                    return false;
                }


                str = DesUtils.decrypt(str, userSecurity.getDesKey());
                User user = JSON.toJavaObject(JSON.parseObject(str), User.class);

                if (user != null) {
                    user = userMapper.selectOne(user);
                    if (user != null) {
                        loginSuccess = true;
                        user.setPassword(null);
                        request.getSession().setAttribute(Constants.SESSION_USER_KEY, user);
                    }
                }
            }

        } catch (Exception ignored) {
        }

        if (!loginSuccess) {
            CookieUtils.removeCookie(response, Constants.COOKIE_LOGIN_KEY_FBLOG);
        }
        return loginSuccess;
    }


    public void vilidateAuth(HttpServletRequest request,
                             HttpServletResponse response,
                             RequestBean requestBean) {
        String service = requestBean.getService();
        //非登录排除且未登录
        if (!authConfiguration.getExcludes().contains(service)
                && StringUtils.isEmpty(request.getSession().getAttribute(Constants.SESSION_USER_KEY))
                && !cookieLogin(request, response)) {
            Throws.throwError(LoginError.NO_AUTH_ERROR);
        }

        //权限


    }


    /**
     * 判断当前登录用户是否拥有权限
     *
     * @param request
     * @param permissions
     * @return
     */
    public boolean currentUserHasPermissions(HttpServletRequest request,
                                             String[] permissions,
                                             Logical logical) {
        UserBean userBean = (UserBean) request.getSession().getAttribute(Constants.SESSION_USER_KEY);

        if (userBean == null) {
            return false;
        }

        if ("20151106".equals(userBean.getId())) {
            //超管
            return true;
        }

        List<String> permissionList = permissionMapper.selectRolePermissionList(userBean.getRoleId());

        if (Logical.AND.equals(logical)) {

            return permissionList.containsAll(Arrays.asList(permissions));
        }

        return ListUtils.containsAny(permissionList, Arrays.asList(permissions));
    }
}
