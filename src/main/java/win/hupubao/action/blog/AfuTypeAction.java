package win.hupubao.action.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import win.hupubao.action.BaseAction;
import win.hupubao.beans.biz.AfuTypeBean;
import win.hupubao.beans.sys.PageBean;
import win.hupubao.beans.sys.RequestBean;
import win.hupubao.beans.sys.ResponseBean;
import win.hupubao.common.error.SystemError;
import win.hupubao.common.error.Throws;
import win.hupubao.common.utils.StringUtils;
import win.hupubao.core.annotation.ServiceInfo;
import win.hupubao.service.AfuTypeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author W.feihong
 * @date 2018-08-02
 */
@Component("afu.type")
public class AfuTypeAction extends BaseAction {

    @Autowired
    private AfuTypeService afuTypeService;

    @ServiceInfo(value = "list", permissions = {"afuType:view"})
    public String afuTypes(HttpServletRequest request,
                       HttpServletResponse response,
                       RequestBean requestBean) {

        PageBean<AfuTypeBean> pageBean = getPageBean(requestBean);
        try {
            AfuTypeBean afuTypeBean = getEntity(requestBean, AfuTypeBean.class);
            pageBean = afuTypeService.selectAfuTypeList(afuTypeBean, pageBean);
            pageBean.success();
        } catch (Exception e) {
            pageBean.error(e);
        }
        return pageBean.serialize();
    }

    @ServiceInfo(value = "edit", permissions = {"afuType:edit"})
    public String edit(HttpServletRequest request,
                       HttpServletResponse response,
                       RequestBean requestBean) {

        ResponseBean responseBean = createResponseBean(requestBean);
        try {
            AfuTypeBean afuTypeBean = getEntity(requestBean, AfuTypeBean.class);

            afuTypeService.edit(afuTypeBean);

            responseBean.success();
        } catch (Exception e) {
            e.printStackTrace();
            responseBean.error(e);
        }
        return responseBean.serialize();
    }

    @ServiceInfo(value = "detail", permissions = {"afuType:view"})
    public String detail(HttpServletRequest request,
                       HttpServletResponse response,
                       RequestBean requestBean) {

        ResponseBean responseBean = createResponseBean(requestBean);
        try {
            AfuTypeBean afuTypeBean = getEntity(requestBean, AfuTypeBean.class);


            if (StringUtils.isBlank(afuTypeBean.getId())) {
                Throws.throwError(SystemError.PARAMETER_ERROR);
            }


            responseBean.success(afuTypeService.selectByPrimaryKey(afuTypeBean.getId()));
        } catch (Exception e) {
            e.printStackTrace();
            responseBean.error(e);
        }
        return responseBean.serialize();
    }

    @ServiceInfo(value = "delete", permissions = {"afuType:delete"})
    public String delete(HttpServletRequest request,
                         HttpServletResponse response,
                         RequestBean requestBean) {

        ResponseBean responseBean = createResponseBean(requestBean);
        try {
            AfuTypeBean afuTypeBean = getEntity(requestBean, AfuTypeBean.class);

            if (StringUtils.isEmpty(afuTypeBean.getId())) {
                Throws.throwError(SystemError.PARAMETER_ERROR);
            }

            afuTypeService.deleteById(afuTypeBean.getId());
            responseBean.success();
        } catch (Exception e) {
            responseBean.error(e);
        }
        return responseBean.serialize();
    }
}
