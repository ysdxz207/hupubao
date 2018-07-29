package win.hupubao.action.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import win.hupubao.action.BaseAction;
import win.hupubao.beans.biz.ArticleBean;
import win.hupubao.beans.sys.PageBean;
import win.hupubao.beans.sys.RequestBean;
import win.hupubao.beans.sys.ResponseBean;
import win.hupubao.common.error.SystemError;
import win.hupubao.common.error.Throws;
import win.hupubao.common.utils.StringUtils;
import win.hupubao.core.annotation.ServiceInfo;
import win.hupubao.domain.Article;
import win.hupubao.service.ArticleService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    public String articles(HttpServletRequest request,
                           HttpServletResponse response,
                           RequestBean requestBean) {

        PageBean<Article> pageBean = getPageBean(requestBean);
        try {
            Article article = getEntity(requestBean, Article.class);
            pageBean = articleService.selectArticleList(article, pageBean);
            pageBean.success();
        } catch (Exception e) {
            pageBean.error(e);
        }
        return pageBean.serialize();
    }

    @ServiceInfo(value = "detail", permissions = {"article:view"})
    public String detail(HttpServletRequest request,
                         HttpServletResponse response,
                         RequestBean requestBean) {

        ResponseBean responseBean = createResponseBean(requestBean);
        try {
            Article article = getEntity(requestBean, Article.class);
            ArticleBean articleBean = articleService.selectArticleBean(article.getId());

            responseBean.success(articleBean);
        } catch (Exception e) {
            responseBean.error(e);
        }
        return responseBean.serialize();
    }

    @ServiceInfo(value = "edit", permissions = {"article:edit"})
    public String edit(HttpServletRequest request,
                         HttpServletResponse response,
                         RequestBean requestBean) {

        ResponseBean responseBean = createResponseBean(requestBean);
        try {
            ArticleBean articleBean = getEntity(requestBean, ArticleBean.class);

            articleService.inserOrUpdate(articleBean);

            responseBean.success();
        } catch (Exception e) {
            responseBean.error(e);
        }
        return responseBean.serialize();
    }

    @ServiceInfo(value = "delete", permissions = {"article:edit"})
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
