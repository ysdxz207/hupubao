package win.hupubao.action.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sqlite.SQLiteException;
import win.hupubao.action.BaseAction;
import win.hupubao.beans.biz.PermissionBean;
import win.hupubao.beans.biz.RoleBean;
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
import win.hupubao.domain.Permission;
import win.hupubao.service.PermissionService;
import win.hupubao.service.RoleService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author W.feihong
 * @date 2018-08-05
 * 文章标签
 */
@Component("role")
public class RoleAction extends BaseAction {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @ServiceInfo(value = "list", permissions = {"role:view"})
    public String roles(HttpServletRequest request,
                       HttpServletResponse response,
                       RequestBean requestBean) {

        PageBean<RoleBean> pageBean = getPageBean(requestBean);
        try {
            RoleBean roleBean = getEntity(requestBean, RoleBean.class);
            pageBean = roleService.selectRoleList(roleBean, pageBean);
            pageBean.success();
        } catch (Exception e) {
            LoggerUtils.error("[角色列表异常]", e);
            pageBean.error(e);
        }
        return pageBean.serialize();
    }

    @ServiceInfo(value = "edit", permissions = {"role:edit"})
    public String edit(HttpServletRequest request,
                       HttpServletResponse response,
                       RequestBean requestBean) {

        ResponseBean responseBean = createResponseBean(requestBean);
        RoleBean roleBean = getEntity(requestBean, RoleBean.class);
        try {

            roleService.edit(roleBean);

            responseBean.success();
        } catch (Exception e) {
            LoggerUtils.error("[编辑角色异常][{}]",roleBean.getId(), e);
            responseBean.error(e);
        }
        return responseBean.serialize();
    }

    @ServiceInfo(value = "delete", permissions = {"role:delete"})
    public String delete(HttpServletRequest request,
                         HttpServletResponse response,
                         RequestBean requestBean) {

        ResponseBean responseBean = createResponseBean(requestBean);
        RoleBean roleBean = getEntity(requestBean, RoleBean.class);
        try {

            if (StringUtils.isEmpty(roleBean.getId())) {
                Throws.throwError(SystemError.PARAMETER_ERROR);
            }

            roleService.deleteById(roleBean.getId());
            responseBean.success();
        } catch (Exception e) {
            LoggerUtils.error("[删除角色异常][{}]",roleBean.getId(), e);
            responseBean.error(e);
        }
        return responseBean.serialize();
    }



    @ServiceInfo(value = "permissions", permissions = {"permission:view"})
    public String permissions(HttpServletRequest request,
                                 HttpServletResponse response,
                                 RequestBean requestBean) {

        ResponseBean responseBean = createResponseBean(requestBean);
        try {
            UserBean userBean = requestBean.toEntity(UserBean.class);

            if (StringUtils.isBlank(userBean.getRoleId())) {
                Throws.throwError(SystemError.PARAMETER_ERROR,
                        "Argument [roleId] should not be null.");
            }

            List<PermissionBean> permissionList = permissionService.selectRolePermissionList(userBean.getRoleId());
            responseBean.success(permissionList.stream().map(Permission::getId).toArray());
        } catch (Exception e) {
            LoggerUtils.error("[获取角色权限异常]", e);
            responseBean.error(e);
        }
        return responseBean.serialize();
    }
}
