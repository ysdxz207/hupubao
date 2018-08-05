package win.hupubao.action.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import win.hupubao.action.BaseAction;
import win.hupubao.beans.biz.MenuBean;
import win.hupubao.beans.sys.PageBean;
import win.hupubao.beans.sys.RequestBean;
import win.hupubao.beans.sys.ResponseBean;
import win.hupubao.common.beans.ResponseBase;
import win.hupubao.common.error.SystemError;
import win.hupubao.common.error.Throws;
import win.hupubao.common.utils.LoggerUtils;
import win.hupubao.common.utils.StringUtils;
import win.hupubao.core.annotation.ServiceInfo;
import win.hupubao.domain.Menu;
import win.hupubao.service.MenuService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author W.feihong
 * @date 2018-07-28 12:37:55
 * 菜单
 */
@Component("menu")
public class MenuAction extends BaseAction {

    @Autowired
    private MenuService menuService;

    @ServiceInfo(value = "type", permissions = {"menu:view"})
    public String type(HttpServletRequest request,
                          HttpServletResponse response,
                          RequestBean requestBean) {
        ResponseBean responseBean = createResponseBean(requestBean);

        try {
            List<MenuBean> menuList = menuService.selectMenuTypeList();
            responseBean.success(menuList);
        } catch (Exception e) {
            responseBean.error(e);
        }
        return responseBean.serialize();
    }

    @ServiceInfo(value = "list", permissions = {"menu:view"})
    public String menus(HttpServletRequest request,
                              HttpServletResponse response,
                              RequestBean requestBean) {

        PageBean<MenuBean> pageBean = getPageBean(requestBean);
        try {
            MenuBean menuBean = getEntity(requestBean, MenuBean.class);
            pageBean = menuService.selectMenuList(menuBean, pageBean);
            pageBean.success();
        } catch (Exception e) {
            pageBean.error(e);
        }
        return pageBean.serialize();
    }

    @ServiceInfo(value = "nav", permissions = {"menu:view"})
    public String navMenu(HttpServletRequest request,
                        HttpServletResponse response,
                        RequestBean requestBean) {
        ResponseBean responseBean = createResponseBean(requestBean);

        try {
            MenuBean menuBean = getEntity(requestBean, MenuBean.class);

            if (StringUtils.isEmpty(menuBean.getType())) {
                Throws.throwError(SystemError.PARAMETER_ERROR, "Parameter [type] should not be null.");
            }
            List<MenuBean> menuList = menuService.selectMenuNav("root", menuBean.getType());
            responseBean.success(menuList);
        } catch (Exception e) {
            LoggerUtils.error("[侧边菜单列表异常]", e);
            responseBean.error(e);
        }
        return responseBean.serialize();
    }
}
