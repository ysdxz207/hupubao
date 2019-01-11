package win.hupubao.action.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import win.hupubao.action.BaseAction;
import win.hupubao.beans.biz.AfuBean;
import win.hupubao.beans.biz.TagBean;
import win.hupubao.beans.sys.PageBean;
import win.hupubao.beans.sys.RequestBean;
import win.hupubao.beans.sys.ResponseBean;
import win.hupubao.common.annotations.LogReqResArgs;
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
 * @author ysdxz207
 * @date 2018-08-02
 * @dockit 阿福接口
 */
@Component("afu")
public class AfuAction extends BaseAction {

    @Autowired
    private AfuService afuService;

    /**
     * @title 阿福列表接口
     * @desc 获取阿福列表
     * @url afu.list
     * @version 1.0.0
     * @status 可用
     * @method POST
     * @arg pageNum,Integer,是,页码
     * @arg pageSize,Integer,否,分页大小
     * @resArg service,Object,是,请求服务，原样返回
     * @resArg service.subObject,Object,是,service子对象
     * @resArg service.subObject.id,Integer,是,service子对象ID
     * @resArg service.subObject.name,String,是,service子对象名
     * @resArg service.subArray,Array,是,service子数组
     * @resArg service.subArray.name,String,是,service子数组名
     * @resArg list,Array,是,阿福列表
     * @resArg list.id,String,是,ID
     * @resArg list.name,String,是,名称
     * @resArg list.type,String,是,类型
     * @resArg list.createTime,String,是,创建时间
     * @resArg list.content,String,是,内容
     * @resArg list.arr,Array,是,数组
     * @resArg list.arr.arrName,String,是,数组名
     * @resArg list.arr.arrValue,String,是,数组值
     * @resArg list.arr.subArr,Array,是,子数组
     * @resArg list.arr.subArr.subArrId,String,是,子数组ID
     * @resArg list.arr.subArr.subArrName,String,是,子数组名
     * @resArg list.arr.subObj,Object,是,子对象
     * @resArg list.arr.subObj.objId,String,是,子对象ID
     * @resArg list.arr.subObj.objName,String,是,子对象名
     *
     */
    @ServiceInfo(value = "list", permissions = {"afu:view"})
    @LogReqResArgs
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
    @LogReqResArgs
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
    @LogReqResArgs
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

    @ServiceInfo(value = "delete", permissions = {"afu:delete"})
    @LogReqResArgs
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
