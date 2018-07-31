package win.hupubao.service;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import win.hupubao.beans.biz.ArticleBean;
import win.hupubao.beans.biz.ArticleTagBean;
import win.hupubao.beans.biz.TagBean;
import win.hupubao.beans.sys.PageBean;
import win.hupubao.common.error.Throws;
import win.hupubao.common.utils.StringUtils;
import win.hupubao.core.errors.ArticleEditError;
import win.hupubao.domain.Article;
import win.hupubao.domain.ArticleTag;
import win.hupubao.domain.Tag;
import win.hupubao.enums.ArticleType;
import win.hupubao.mapper.ArticleMapper;
import win.hupubao.mapper.ArticleTagMapper;
import win.hupubao.mapper.TagMapper;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author W.feihong
 * @date 2018-07-30
 *
 */

@Service
public class ArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private ArticleTagMapper articleTagMapper;

    public PageBean<Article> selectArticleList(Article article,
                                               PageBean<Article> pageBean) {
        PageHelper.startPage(pageBean.getPageNum(),
                pageBean.getPageSize(), "create_date desc");
        List<Article> articleList = articleMapper.select(article);
        pageBean.setList(articleList);
        pageBean.setTotal(articleMapper.selectCount(article));
        return pageBean;
    }

    public ArticleBean detail(String id) {
        return articleMapper.selectArticleDetail(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void edit(ArticleBean articleBean) {
        int n = 0;

        Article article = new Article();
        BeanUtils.copyProperties(articleBean, article);
        if (StringUtils.isEmpty(articleBean.getId())) {
            if (article.getType() == null) {
                article.setType(ArticleType.yiyi.name());
            }
            n = articleMapper.insertSelective(article);
        } else {
            n = articleMapper.updateByPrimaryKeySelective(article);
        }

        if (n == 0) {
            Throws.throwError(ArticleEditError.ARTICLE_EDIT_ERROR);
        }

        //标签
        articleBean.setId(article.getId());
        createArticleTags(articleBean);

    }

    private void createArticleTags(ArticleBean articleBean) {

        if(articleBean == null ||
                articleBean.getTags() == null) {
            return;
        }

        String[] tagNameList = articleBean.getTags().split(",");

        //删除文章关联的已存在的标签
        if (articleBean.getId() != null) {

            Example example = new Example(ArticleTag.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("articleId", articleBean.getId());
            articleTagMapper.deleteByExample(example);
        }
        //创建并关联标签
        for (String tagName : tagNameList) {
            if (StringUtils.isEmpty(tagName)) {
                continue;
            }
            Tag tag = new Tag();
            tag.setName(tagName);
            Tag tagDB = tagMapper.selectOne(tag);
            if (tagDB == null) {
                tagMapper.insertSelective(tag);
            } else {
                BeanUtils.copyProperties(tagDB, tag);
            }

            ArticleTag articleTag = new ArticleTag();
            articleTag.setArticleId(articleBean.getId());
            articleTag.setTagId(tag.getId());
            articleTagMapper.insertSelective(articleTag);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        articleMapper.deleteByPrimaryKey(id);
    }
}