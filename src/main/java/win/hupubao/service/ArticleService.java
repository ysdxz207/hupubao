package win.hupubao.service;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import win.hupubao.beans.biz.ArticleBean;
import win.hupubao.beans.sys.PageBean;
import win.hupubao.common.error.Throws;
import win.hupubao.common.utils.StringUtils;
import win.hupubao.core.errors.ArticleEditError;
import win.hupubao.domain.Article;
import win.hupubao.mapper.ArticleMapper;

import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    public PageBean<Article> selectArticleList(Article article,
                                               PageBean<Article> pageBean) {
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
        List<Article> articleList = articleMapper.select(article);
        pageBean.setList(articleList);
        pageBean.setTotal(articleMapper.selectCount(article));
        return pageBean;
    }

    public ArticleBean selectArticleBean(String id) {
        ArticleBean articleBean = new ArticleBean();
        Article article = articleMapper.selectByPrimaryKey(id);
        BeanUtils.copyProperties(article, articleBean);
        return articleBean;
    }

    public void inserOrUpdate(ArticleBean articleBean) {
        int n = 0;

        Article article = new Article();
        BeanUtils.copyProperties(articleBean, article);
        //标签

        //分类

        if (StringUtils.isEmpty(articleBean.getId())) {
            n = articleMapper.insertSelective(article);
        } else {
            n = articleMapper.updateByPrimaryKeySelective(article);
        }

        if (n == 0) {
            Throws.throwError(ArticleEditError.ARTICLE_EDIT_ERROR);
        }
    }

    public void deleteById(String id) {
        if (articleMapper.deleteByPrimaryKey(id) == 0) {
            Throws.throwError(ArticleEditError.ARTICLE_DELETE_ERROR);
        }
    }
}
