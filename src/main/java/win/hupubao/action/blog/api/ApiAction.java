package win.hupubao.action.blog.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;
import win.hupubao.action.BaseAction;
import win.hupubao.beans.biz.AfuBean;
import win.hupubao.beans.biz.AfuTypeBean;
import win.hupubao.beans.sys.PageBean;
import win.hupubao.beans.sys.RequestBean;
import win.hupubao.beans.sys.ResponseBean;
import win.hupubao.common.annotations.LogReqResArgs;
import win.hupubao.common.error.SystemError;
import win.hupubao.common.error.Throws;
import win.hupubao.common.utils.LoggerUtils;
import win.hupubao.common.utils.StringUtils;
import win.hupubao.core.annotation.ServiceInfo;
import win.hupubao.core.errors.AfuEditError;
import win.hupubao.service.AfuService;
import win.hupubao.service.AfuTypeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component("api")
public class ApiAction extends BaseAction {

    @Autowired
    private AfuService afuService;
    @Autowired
    private AfuTypeService afuTypeService;



    @ServiceInfo(value = "afu.list")
    @LogReqResArgs
    public String afus(HttpServletRequest request,
                       HttpServletResponse response,
                       RequestBean requestBean) {

        PageBean<AfuBean> pageBean = getPageBean(requestBean);
        try {

            AfuBean afuBean = getEntity(requestBean, AfuBean.class);
            if (StringUtils.isBlank(afuBean.getType())) {
                Throws.throwError(SystemError.PARAMETER_ERROR, "Argument [type] can not be null.");
            }
            AfuTypeBean afuTypeBean = afuTypeService.selectByPrimaryKey(afuBean.getType());
            if (afuTypeBean == null) {
                Throws.throwError(SystemError.PARAMETER_ERROR, "阿福类别不存在");
            }
            verifySign(requestBean, afuTypeBean.getPrivateKey(), afuTypeBean.getPublicKey());
            pageBean = afuService.selectAfuList(afuBean, pageBean);
            pageBean.success();
        } catch (Exception e) {
            LoggerUtils.error("[阿福列表异常]", e);
            pageBean.error(e);
        }
        return pageBean.serialize();
    }

    @ServiceInfo(value = "afu.edit")
    @LogReqResArgs
    public String afuEdit(HttpServletRequest request,
                          HttpServletResponse response,
                          RequestBean requestBean) {

        ResponseBean responseBean = createResponseBean(requestBean);
        try {
            AfuBean afuBean = getEntity(requestBean, AfuBean.class);
            if (StringUtils.isBlank(afuBean.getType())) {
                Throws.throwError(SystemError.PARAMETER_ERROR, "Argument [type] can not be null.");
            }
            if (StringUtils.isBlank(afuBean.getName())) {
                Throws.throwError(SystemError.PARAMETER_ERROR, "Argument [name] can not be null.");
            }
            if (StringUtils.isBlank(afuBean.getContent())) {
                Throws.throwError(SystemError.PARAMETER_ERROR, "Argument [content] can not be null.");
            }
            AfuTypeBean afuTypeBean = afuTypeService.selectByPrimaryKey(afuBean.getType());
            if (afuTypeBean == null) {
                Throws.throwError(SystemError.PARAMETER_ERROR, "阿福类别不存在");
            }
            verifySign(requestBean, afuTypeBean.getPrivateKey(), afuTypeBean.getPublicKey());
            //防重复

            Example example = new Example(AfuBean.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("name", afuBean.getName());
            List<AfuBean> afus = afuService.selectByExample(example);
            AfuBean afuDB = null;
            if (afus != null && afus.size() == 1) {
                afuDB = afus.get(0);
                afuBean.setId(afuDB.getId());
            }
            afuService.edit(afuBean);

            responseBean.success(afuBean);
        } catch (Exception e) {
            e.printStackTrace();
            responseBean.error(e);
        }
        return responseBean.serialize();
    }

    @ServiceInfo(value = "afu.detail")
    @LogReqResArgs
    public String afuDetail(HttpServletRequest request,
                            HttpServletResponse response,
                            RequestBean requestBean) {

        ResponseBean responseBean = createResponseBean(requestBean);
        try {
            AfuBean afuBean = getEntity(requestBean, AfuBean.class);
            if (StringUtils.isBlank(afuBean.getType())) {
                Throws.throwError(SystemError.PARAMETER_ERROR, "Argument [type] can not be null.");
            }
            AfuTypeBean afuTypeBean = afuTypeService.selectByPrimaryKey(afuBean.getType());
            if (afuTypeBean == null) {
                Throws.throwError(SystemError.PARAMETER_ERROR, "阿福类别不存在");
            }
            verifySign(requestBean, afuTypeBean.getPrivateKey(), afuTypeBean.getPublicKey());
            afuBean = afuService.detail(afuBean.getId());
            responseBean.success(afuBean);
        } catch (Exception e) {
            responseBean.error(e);
            LoggerUtils.error(e);
        }
        return responseBean.serialize();
    }

    @ServiceInfo(value = "afu.delete")
    @LogReqResArgs
    public String afuDelete(HttpServletRequest request,
                            HttpServletResponse response,
                            RequestBean requestBean) {

        ResponseBean responseBean = createResponseBean(requestBean);
        try {
            AfuBean afuBean = getEntity(requestBean, AfuBean.class);
            if (StringUtils.isBlank(afuBean.getType())) {
                Throws.throwError(SystemError.PARAMETER_ERROR, "Argument [type] can not be null.");
            }
            AfuTypeBean afuTypeBean = afuTypeService.selectByPrimaryKey(afuBean.getType());
            if (afuTypeBean == null) {
                Throws.throwError(SystemError.PARAMETER_ERROR, "阿福类别不存在");
            }
            verifySign(requestBean, afuTypeBean.getPrivateKey(), afuTypeBean.getPublicKey());
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
