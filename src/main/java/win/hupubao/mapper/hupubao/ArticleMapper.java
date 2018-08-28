package win.hupubao.mapper.hupubao;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;
import win.hupubao.beans.biz.ArticleBean;
import win.hupubao.domain.Article;
import win.hupubao.utils.mybatis.MyMapper;

import java.util.List;

@Repository
public interface ArticleMapper extends MyMapper<Article> {


    ArticleBean selectArticleDetail(@Param("id") String id);

    List<ArticleBean> selectList(ArticleBean articleBean);
}
