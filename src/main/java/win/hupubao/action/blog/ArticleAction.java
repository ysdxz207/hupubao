package win.hupubao.action.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import win.hupubao.action.BaseAction;
import win.hupubao.beans.biz.ArticleBean;
import win.hupubao.beans.biz.TagBean;
import win.hupubao.beans.biz.UserBean;
import win.hupubao.beans.sys.PageBean;
import win.hupubao.beans.sys.RequestBean;
import win.hupubao.beans.sys.ResponseBean;
import win.hupubao.common.annotations.LogReqResArgs;
import win.hupubao.common.error.SystemError;
import win.hupubao.common.error.Throws;
import win.hupubao.common.utils.LoggerUtils;
import win.hupubao.common.utils.StringUtils;
import win.hupubao.constants.Constants;
import win.hupubao.core.annotation.ServiceInfo;
import win.hupubao.domain.Article;
import win.hupubao.service.ArticleService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * @author W.feihong
 * @date 2018-07-29
 * 文章
 */
@Component("article")
public class ArticleAction extends BaseAction {

    @Autowired
    private ArticleService articleService;

    @ServiceInfo(value = "list", permissions = {"article:view"})
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
            LoggerUtils.error("[文章列表异常]", e);
        }
        return pageBean.serialize();
    }

    @ServiceInfo(value = "detail", permissions = {"article:view"})
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

    @ServiceInfo(value = "edit", permissions = {"article:edit"})
    @LogReqResArgs
    public String edit(HttpServletRequest request,
                         HttpServletResponse response,
                         RequestBean requestBean) {

        ResponseBean responseBean = createResponseBean(requestBean);
        try {
            ArticleBean articleBean = getEntity(requestBean, ArticleBean.class);

            if (StringUtils.isBlank(articleBean.getId())) {
                UserBean userBean = (UserBean)request.getSession().getAttribute(Constants.SESSION_USER_KEY);
                articleBean.setCreateTime(new Date());
                articleBean.setCreator(userBean.getUsername());
            }
            articleService.edit(articleBean);

            responseBean.success();
        } catch (Exception e) {
            e.printStackTrace();
            responseBean.error(e);
        }
        return responseBean.serialize();
    }

    @ServiceInfo(value = "delete", permissions = {"article:delete"})
    @LogReqResArgs
    public String delete(HttpServletRequest request,
                       HttpServletResponse response,
                       RequestBean requestBean) {

        ResponseBean responseBean = createResponseBean(requestBean);
        try {
            ArticleBean articleBean = getEntity(requestBean, ArticleBean.class);

            if (StringUtils.isEmpty(articleBean.getId())) {
                Throws.throwError(SystemError.PARAMETER_ERROR);
            }

            articleService.deleteById(articleBean.getId());
            responseBean.success();
        } catch (Exception e) {
            responseBean.error(e);
        }
        return responseBean.serialize();
    }
}
