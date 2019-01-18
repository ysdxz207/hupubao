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
import win.hupubao.core.annotation.NeedVerifySign;
import win.hupubao.core.annotation.ServiceInfo;
import win.hupubao.errors.AfuError;
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
    @NeedVerifySign
    public ResponseBean afus(HttpServletRequest request,
                             HttpServletResponse response,
                             RequestBean requestBean) {

        PageBean<AfuBean> pageBean = getPageBean(requestBean);


        AfuTypeBean afuTypeBean = afuTypeService.selectByPrimaryKey(requestBean.getAfuType());
        if (afuTypeBean == null) {
            Throws.throwError(SystemError.PARAMETER_ERROR, "阿福类别不存在");
        }

        AfuBean afuBean = new AfuBean();
        afuBean.setType(afuTypeBean.getId());
        pageBean = afuService.selectAfuList(afuBean, pageBean);
        pageBean.success();
        return pageBean;
    }

    @ServiceInfo(value = "afu.edit")
    @LogReqResArgs
    @NeedVerifySign
    public ResponseBean afuEdit(HttpServletRequest request,
                                HttpServletResponse response,
                                RequestBean requestBean) {

        ResponseBean responseBean = createResponseBean(requestBean);
        AfuBean afuBean = getEntity(requestBean, AfuBean.class);
        if (StringUtils.isBlank(afuBean.getName())) {
            Throws.throwError(SystemError.PARAMETER_ERROR, "Argument [name] can not be null.");
        }
        if (StringUtils.isBlank(afuBean.getContent())) {
            Throws.throwError(SystemError.PARAMETER_ERROR, "Argument [content] can not be null.");
        }
        AfuBean afuDB = null;

        if (afuBean.getId() != null) {
            // 修改
            afuDB = afuService.detail(afuBean.getId());

            if (afuDB == null) {
                Throws.throwError(AfuError.AFU_NOT_EXITS_ERROR);
            }
        } else {
            // 新增或修改
            Example example = new Example(AfuBean.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("name", afuBean.getName());
            List<AfuBean> afus = afuService.selectByExample(example);
            if (afus != null && afus.size() > 0) {
                afuDB = afus.get(0);
            }
        }

        if (afuDB != null) {
            afuBean.setId(afuDB.getId());
        }

        afuService.edit(afuBean);

        responseBean.success();
        return responseBean;
    }

    @ServiceInfo(value = "afu.detail")
    @LogReqResArgs
    @NeedVerifySign
    public ResponseBean afuDetail(HttpServletRequest request,
                                  HttpServletResponse response,
                                  RequestBean requestBean) {

        ResponseBean responseBean = createResponseBean(requestBean);
        AfuBean afuBean = getEntity(requestBean, AfuBean.class);
        if (StringUtils.isBlank(afuBean.getId())) {
            Throws.throwError(SystemError.PARAMETER_ERROR, "Argument [id] can not be null.");
        }
        afuBean = afuService.detail(afuBean.getId());

        if (afuBean == null) {
            Throws.throwError(AfuError.AFU_NOT_EXITS_ERROR);
        }

        responseBean.success(afuBean);
        return responseBean;
    }

    @ServiceInfo(value = "afu.delete")
    @LogReqResArgs
    @NeedVerifySign
    public ResponseBean afuDelete(HttpServletRequest request,
                                  HttpServletResponse response,
                                  RequestBean requestBean) {

        ResponseBean responseBean = createResponseBean(requestBean);
        AfuBean afuBean = getEntity(requestBean, AfuBean.class);
        if (StringUtils.isBlank(afuBean.getId())) {
            Throws.throwError(SystemError.PARAMETER_ERROR, "Argument [id] can not be null.");
        }
        afuService.deleteById(afuBean.getId());
        responseBean.success();
        return responseBean;
    }

}
