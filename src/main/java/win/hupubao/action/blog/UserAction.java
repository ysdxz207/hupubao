package win.hupubao.action.blog;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import win.hupubao.action.BaseAction;
import win.hupubao.beans.biz.UserBean;
import win.hupubao.beans.sys.RequestBean;
import win.hupubao.beans.sys.ResponseBean;
import win.hupubao.common.error.SystemError;
import win.hupubao.common.error.Throws;
import win.hupubao.common.utils.StringUtils;
import win.hupubao.constants.Constants;
import win.hupubao.core.annotation.ServiceInfo;
import win.hupubao.domain.User;
import win.hupubao.service.UserService;

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
                Throws.throwError(SystemError.PARAMETER_ERROR, "Parameter [id] should not be null.");
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
}
