package win.hupubao.action.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import win.hupubao.action.BaseAction;
import win.hupubao.beans.biz.PermissionBean;
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
import win.hupubao.service.PermissionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author W.feihong
 * @date 2018-08-05
 * 权限
 */
@Component("permission")
public class PermissionAction extends BaseAction {

    @Autowired
    private PermissionService permissionService;

    @ServiceInfo(value = "list", permissions = {"permission:view"})
    public String permissions(HttpServletRequest request,
                       HttpServletResponse response,
                       RequestBean requestBean) {

        PageBean<PermissionBean> pageBean = getPageBean(requestBean);
        try {
            PermissionBean permissionBean = getEntity(requestBean, PermissionBean.class);
            pageBean = permissionService.selectPermissionList(permissionBean, pageBean);
            pageBean.success();
        } catch (Exception e) {
            LoggerUtils.error("[权限列表异常]", e);
            pageBean.error(e);
        }
        return pageBean.serialize();
    }

    @ServiceInfo(value = "edit", permissions = {"permission:edit"})
    public String edit(HttpServletRequest request,
                       HttpServletResponse response,
                       RequestBean requestBean) {

        ResponseBean responseBean = createResponseBean(requestBean);
        PermissionBean permissionBean = getEntity(requestBean, PermissionBean.class);
        try {

            permissionService.edit(permissionBean);

            responseBean.success();
        } catch (Exception e) {
            LoggerUtils.error("[编辑权限异常][{}]", permissionBean.getId(), e);
            responseBean.error(e);
        }
        return responseBean.serialize();
    }

    @ServiceInfo(value = "delete", permissions = {"permission:delete"})
    public String delete(HttpServletRequest request,
                         HttpServletResponse response,
                         RequestBean requestBean) {

        ResponseBean responseBean = createResponseBean(requestBean);
        PermissionBean permissionBean = getEntity(requestBean, PermissionBean.class);
        try {

            if (StringUtils.isEmpty(permissionBean.getId())) {
                Throws.throwError(SystemError.PARAMETER_ERROR);
            }

            permissionService.deleteById(permissionBean.getId());
            responseBean.success();
        } catch (Exception e) {
            LoggerUtils.error("[删除权限异常][{}]", permissionBean.getId(), e);
            responseBean.error(e);
        }
        return responseBean.serialize();
    }

    @ServiceInfo(value = "validateList", permissions = {"permission:view"})
    public String validatePermissionList(HttpServletRequest request,
                              HttpServletResponse response,
                              RequestBean requestBean) {

        ResponseBean responseBean = createResponseBean(requestBean);
        try {
            UserBean userBean = (UserBean) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
            List<PermissionBean> list = permissionService.selectValidatePermissionList(userBean.getRoleId());
            responseBean.success(list);
        } catch (Exception e) {
            LoggerUtils.error("[有效权限列表异常]", e);
            responseBean.error(e);
        }
        return responseBean.serialize();
    }
}
