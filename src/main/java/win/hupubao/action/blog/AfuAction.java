package win.hupubao.action.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import win.hupubao.action.BaseAction;
import win.hupubao.beans.biz.AfuBean;
import win.hupubao.beans.biz.TagBean;
import win.hupubao.beans.sys.PageBean;
import win.hupubao.beans.sys.RequestBean;
import win.hupubao.beans.sys.ResponseBean;
import win.hupubao.common.error.SystemError;
import win.hupubao.common.error.Throws;
import win.hupubao.common.utils.LoggerUtils;
import win.hupubao.common.utils.StringUtils;
import win.hupubao.core.annotation.ServiceInfo;
import win.hupubao.domain.Afu;
import win.hupubao.service.AfuService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

/**
 * @author W.feihong
 * @date 2018-08-02
 */
@Component("afu")
public class AfuAction extends BaseAction {

    @Autowired
    private AfuService afuService;

    @ServiceInfo(value = "list", permissions = {"afu:view"})
    public String afus(HttpServletRequest request,
                       HttpServletResponse response,
                       RequestBean requestBean) {

        PageBean<AfuBean> pageBean = getPageBean(requestBean);
        try {
            AfuBean afuBean = getEntity(requestBean, AfuBean.class);
            pageBean = afuService.selectAfuList(afuBean, pageBean);
            pageBean.success();
        } catch (Exception e) {
            LoggerUtils.error("[阿福列表异常]", e);
            pageBean.error(e);
        }
        return pageBean.serialize();
    }

    @ServiceInfo(value = "edit", permissions = {"afu:edit"})
    public String edit(HttpServletRequest request,
                       HttpServletResponse response,
                       RequestBean requestBean) {

        ResponseBean responseBean = createResponseBean(requestBean);
        try {
            AfuBean afuBean = getEntity(requestBean, AfuBean.class);

            afuService.edit(afuBean);

            responseBean.success();
        } catch (Exception e) {
            e.printStackTrace();
            responseBean.error(e);
        }
        return responseBean.serialize();
    }

    @ServiceInfo(value = "detail", permissions = {"afu:view"})
    public String detail(HttpServletRequest request,
                         HttpServletResponse response,
                         RequestBean requestBean) {

        ResponseBean responseBean = createResponseBean(requestBean);
        try {
            Afu afu = getEntity(requestBean, Afu.class);
            AfuBean afuBean = afuService.detail(afu.getId());
            responseBean.success(afuBean);
        } catch (Exception e) {
            responseBean.error(e);
            LoggerUtils.error(e);
        }
        return responseBean.serialize();
    }

    @ServiceInfo(value = "delete", permissions = {"afu:edit"})
    public String delete(HttpServletRequest request,
                         HttpServletResponse response,
                         RequestBean requestBean) {

        ResponseBean responseBean = createResponseBean(requestBean);
        try {
            AfuBean afuBean = getEntity(requestBean, AfuBean.class);

            if (StringUtils.isEmpty(afuBean.getId())) {
                Throws.throwError(SystemError.PARAMETER_ERROR);
            }

            afuService.deleteById(afuBean.getId());
            responseBean.success();
        } catch (Exception e) {
            responseBean.error(e);
        }
        return responseBean.serialize();
    }
}
