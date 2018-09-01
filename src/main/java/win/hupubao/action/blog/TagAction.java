package win.hupubao.action.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import win.hupubao.action.BaseAction;
import win.hupubao.beans.biz.TagBean;
import win.hupubao.beans.biz.TagBean;
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
import win.hupubao.domain.Tag;
import win.hupubao.domain.Tag;
import win.hupubao.service.TagService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author W.feihong
 * @date 2018-07-29
 * 文章标签
 */
@Component("tag")
public class TagAction extends BaseAction {

    @Autowired
    private TagService tagService;

    @ServiceInfo(value = "list", permissions = {"tag:view"})
    public String tags(HttpServletRequest request,
                       HttpServletResponse response,
                       RequestBean requestBean) {

        PageBean<TagBean> pageBean = getPageBean(requestBean);
        try {
            TagBean tagBean = getEntity(requestBean, TagBean.class);
            pageBean = tagService.selectTagList(tagBean, pageBean);
            pageBean.success();
        } catch (Exception e) {
            pageBean.error(e);
            LoggerUtils.error("[标签列表异常]", e);
        }
        return pageBean.serialize();
    }

    @ServiceInfo(value = "edit", permissions = {"tag:edit"})
    public String edit(HttpServletRequest request,
                       HttpServletResponse response,
                       RequestBean requestBean) {

        ResponseBean responseBean = createResponseBean(requestBean);
        try {
            TagBean tagBean = getEntity(requestBean, TagBean.class);

            tagService.edit(tagBean);

            responseBean.success();
        } catch (Exception e) {
            e.printStackTrace();
            responseBean.error(e);
        }
        return responseBean.serialize();
    }

    @ServiceInfo(value = "delete", permissions = {"tag:delete"})
    public String delete(HttpServletRequest request,
                         HttpServletResponse response,
                         RequestBean requestBean) {

        ResponseBean responseBean = createResponseBean(requestBean);
        try {
            TagBean tagBean = getEntity(requestBean, TagBean.class);

            if (StringUtils.isEmpty(tagBean.getId())) {
                Throws.throwError(SystemError.PARAMETER_ERROR);
            }

            tagService.deleteById(tagBean.getId());
            responseBean.success();
        } catch (Exception e) {
            responseBean.error(e);
        }
        return responseBean.serialize();
    }
}
