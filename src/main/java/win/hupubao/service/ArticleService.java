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
import win.hupubao.mapper.ArticleMapper;
import win.hupubao.mapper.ArticleTagMapper;
import win.hupubao.mapper.TagMapper;

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
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
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

        //标签
        createArticleTags(articleBean);

        Article article = new Article();
        BeanUtils.copyProperties(articleBean, article);
        if (StringUtils.isEmpty(articleBean.getId())) {
            n = articleMapper.insertSelective(article);
        } else {
            n = articleMapper.updateByPrimaryKeySelective(article);
        }

        if (n == 0) {
            Throws.throwError(ArticleEditError.ARTICLE_EDIT_ERROR);
        }
    }

    private void createArticleTags(ArticleBean articleBean) {
        if(articleBean == null ||
                articleBean.getTagList() == null
                || articleBean.getTagList().isEmpty()) {
            return;
        }

        List<TagBean> tagNameList = articleBean.getTagList();

        //删除文章关联的已存在的标签
        if (articleBean.getId() != null) {

            Example example = new Example(ArticleTag.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("articleId", articleBean.getId());
            articleTagMapper.deleteByExample(example);
        }
        //创建并关联标签
        for (TagBean tagBean : tagNameList) {
            Tag tag = new Tag();
            tag.setName(tagBean.getName());
            tag = tagMapper.selectOne(tag);
            if (tag == null) {
                tagMapper.insertSelective(tag);
            } else {
                BeanUtils.copyProperties(tag, tagBean);
            }

            ArticleTagBean articleTagBean = new ArticleTagBean();
            articleTagBean.setArticleId(articleBean.getId());
            articleTagBean.setTagId(tagBean.getId());
            articleTagMapper.insertSelective(articleTagBean);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        if (articleMapper.deleteByPrimaryKey(id) == 0) {
            Throws.throwError(ArticleEditError.ARTICLE_DELETE_ERROR);
        }
    }
}
