package win.hupubao.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;
import win.hupubao.beans.biz.ArticleBean;
import win.hupubao.domain.Article;
import win.hupubao.utils.mybatis.MyMapper;

import java.util.List;

@Repository
public interface ArticleMapper extends MyMapper<Article> {


    @Select("select * from article where id=#{id}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(column = "id", property = "tagList",
                    javaType = List.class,
                    many = @Many(
                            select = "win.hupubao.mapper.TagMapper.selectTagListByArticleId",
                            fetchType = FetchType.EAGER
                    ))
    })
    ArticleBean selectArticleDetail(@Param("id") String id);

    List<ArticleBean> selectList(ArticleBean articleBean);
}
