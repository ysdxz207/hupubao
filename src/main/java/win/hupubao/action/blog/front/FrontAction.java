package win.hupubao.action.blog.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import win.hupubao.action.BaseAction;
import win.hupubao.beans.biz.ArticleBean;
import win.hupubao.beans.biz.CategoryBean;
import win.hupubao.beans.biz.TagBean;
import win.hupubao.beans.sys.PageBean;
import win.hupubao.beans.sys.RequestBean;
import win.hupubao.beans.sys.ResponseBean;
import win.hupubao.common.annotations.LogReqResArgs;
import win.hupubao.common.utils.LoggerUtils;
import win.hupubao.core.annotation.ServiceInfo;
import win.hupubao.domain.Article;
import win.hupubao.service.ArticleService;
import win.hupubao.service.CategoryService;
import win.hupubao.service.TagService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

/**
 * @author ysdxz207
 * @date 2018-08-20
 * 博客前端列表
 */
@Component("front")
public class FrontAction extends BaseAction {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TagService tagService;


    @ServiceInfo(value = "articles")
    @LogReqResArgs
    public String articles(HttpServletRequest request,
                           HttpServletResponse response,
                           RequestBean requestBean) {

        PageBean<ArticleBean> pageBean = getPageBean(requestBean);
        try {
            ArticleBean articleBean = getEntity(requestBean, ArticleBean.class);
            pageBean = articleService.selectArticleList(articleBean, pageBean);
            pageBean.success();
        } catch (Exception e) {
            pageBean.error(e);
            LoggerUtils.error("[front文章列表异常]", e);
        }
        return pageBean.serialize();
    }

    @ServiceInfo(value = "article")
    @LogReqResArgs
    public String detail(HttpServletRequest request,
                         HttpServletResponse response,
                         RequestBean requestBean) {

        ResponseBean responseBean = createResponseBean(requestBean);
        try {
            Article article = getEntity(requestBean, Article.class);
            ArticleBean articleBean = articleService.detail(article.getId());

            if (articleBean.getTagList() != null
                    && !articleBean.getTagList().isEmpty()) {
                String tags = articleBean.getTagList().stream().map(TagBean::getName).collect(Collectors.joining(","));
                articleBean.setTags(tags);
            }
            responseBean.success(articleBean);
        } catch (Exception e) {
            responseBean.error(e);
            LoggerUtils.error(e);
        }
        return responseBean.serialize();
    }


    @ServiceInfo(value = "category")
    @LogReqResArgs
    public String categorys(HttpServletRequest request,
                            HttpServletResponse response,
                            RequestBean requestBean) {

        PageBean<CategoryBean> pageBean = getPageBean(requestBean);
        try {
            pageBean = categoryService.selectCategoryList(new CategoryBean(), pageBean);
            pageBean.success();
        } catch (Exception e) {
            LoggerUtils.error("[front分类列表]异常", e);
            pageBean.error(e);
        }
        return pageBean.serialize();
    }

    @ServiceInfo(value = "tag")
    @LogReqResArgs
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
        }
        return pageBean.serialize();
    }

}
