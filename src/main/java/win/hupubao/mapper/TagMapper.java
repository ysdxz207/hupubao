package win.hupubao.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import win.hupubao.domain.Tag;
import win.hupubao.utils.mybatis.MyMapper;

import java.util.List;

@Repository
public interface TagMapper extends MyMapper<Tag> {


    @Select("select * from tag where id in (select tag_id from article_tag where article_id = ${articleId})")
    List<Tag> selectTagListByArticleId(@Param("articleId") String articleId);
}
