package win.hupubao.action.access;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import win.hupubao.action.BaseAction;
import win.hupubao.beans.biz.UserBean;
import win.hupubao.beans.sys.PageBean;
import win.hupubao.beans.sys.RequestBean;
import win.hupubao.beans.sys.ResponseBean;
import win.hupubao.common.error.SystemError;
import win.hupubao.common.error.Throws;
import win.hupubao.common.utils.LoggerUtils;
import win.hupubao.common.utils.StringUtils;
import win.hupubao.constants.Constants;
import win.hupubao.core.annotation.ServiceInfo;
import win.hupubao.domain.User;
import win.hupubao.service.UserService;
import win.hupubao.utils.CookieUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component("user")
public class UserAction extends BaseAction {

    @Autowired
    private UserService userService;


    @ServiceInfo(value = "login")
    public ResponseBean login(HttpServletRequest request,
                              HttpServletResponse response,
                              RequestBean requestBean) {
        ResponseBean responseBean = new ResponseBean(requestBean.getService());

        try {


            UserBean userBean = getEntity(requestBean, UserBean.class);

            userBean.setSessionCaptcha((String) request.getSession().getAttribute(Constants.CAPTCHA_SESSION_KEY));
            userBean = userService.login(userBean);
            responseBean.success(userBean);
            //登录成功
            userBean.setToken(request.getSession().getId());
            userBean.setId(userBean.getId());
            userService.rememberMe(response, userBean);
            userBean.setPassword(null);
            request.getSession().setAttribute(Constants.SESSION_USER_KEY, userBean);
        } catch (Exception e) {
            responseBean.error(e);
            LoggerUtils.error("[登录异常]", e);
        }
        return responseBean;
    }

    /**
     * 用户信息
     * @param request
     * @param response
     * @param requestBean
     * @return
     */
    @ServiceInfo(value = "info", permissions = {"user:view"})
    public ResponseBean info(HttpServletRequest request,
                             HttpServletResponse response,
                             RequestBean requestBean) {
        ResponseBean responseBean = new ResponseBean(requestBean.getService());

        try {
            User user = getEntity(requestBean, User.class);
            if (StringUtils.isEmpty(user.getId())) {
                Throws.throwError(SystemError.PARAMETER_ERROR, "Argument [id] should not be null.");
            }
            user = userService.selectOne(user);
            if (user != null) {
                UserBean userBean = new UserBean();
                BeanUtils.copyProperties(user, userBean);
                userBean.setPassword(null);
                responseBean.success(userBean);
            }
        } catch (Exception e) {
            responseBean.error(e);
        }
        return responseBean;
    }

    @ServiceInfo(value = "list", permissions = {"user:view"})
    public String users(HttpServletRequest request,
                       HttpServletResponse response,
                       RequestBean requestBean) {

        PageBean<UserBean> pageBean = getPageBean(requestBean);
        try {
            UserBean userBean = getEntity(requestBean, UserBean.class);
            pageBean = userService.selectUserList(userBean, pageBean);
            pageBean.success();
        } catch (Exception e) {
            LoggerUtils.error("[用户列表异常]", e);
            pageBean.error(e);
        }
        return pageBean.serialize();
    }

    @ServiceInfo(value = "edit", permissions = {"user:edit"})
    public String edit(HttpServletRequest request,
                       HttpServletResponse response,
                       RequestBean requestBean) {

        ResponseBean responseBean = createResponseBean(requestBean);
        try {
            UserBean userBean = getEntity(requestBean, UserBean.class);

            userService.edit(userBean);

            responseBean.success();
        } catch (Exception e) {
            e.printStackTrace();
            responseBean.error(e);
        }
        return responseBean.serialize();
    }

    @ServiceInfo(value = "delete", permissions = {"user:delete"})
    public String delete(HttpServletRequest request,
                         HttpServletResponse response,
                         RequestBean requestBean) {

        ResponseBean responseBean = createResponseBean(requestBean);
        try {
            UserBean userBean = getEntity(requestBean, UserBean.class);

            if (StringUtils.isEmpty(userBean.getId())) {
                Throws.throwError(SystemError.PARAMETER_ERROR);
            }

            userService.deleteById(userBean.getId());
            responseBean.success();
        } catch (Exception e) {
            responseBean.error(e);
        }
        return responseBean.serialize();
    }

    @ServiceInfo(value = "logout")
    public ResponseBean logout(HttpServletRequest request,
                              HttpServletResponse response,
                              RequestBean requestBean) {
        ResponseBean responseBean = new ResponseBean(requestBean.getService());

        try {
            request.getSession().removeAttribute(Constants.SESSION_USER_KEY);
            CookieUtils.removeCookie(response, Constants.COOKIE_LOGIN_KEY_FBLOG);
            responseBean.success();
        } catch (Exception e) {
            responseBean.error(e);
            LoggerUtils.error("[退出异常]", e);
        }
        return responseBean;
    }
}
