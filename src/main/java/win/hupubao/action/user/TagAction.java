package win.hupubao.action.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import win.hupubao.action.BaseAction;
import win.hupubao.beans.sys.PageBean;
import win.hupubao.beans.sys.RequestBean;
import win.hupubao.core.annotation.ServiceInfo;
import win.hupubao.domain.Article;
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

        PageBean<Tag> pageBean = getPageBean(requestBean);
        try {
            Tag tag = getEntity(requestBean, Tag.class);
            pageBean = tagService.selectTagList(tag, pageBean);
            pageBean.success();
        } catch (Exception e) {
            pageBean.error(e);
        }
        return pageBean.serialize();
    }
}
