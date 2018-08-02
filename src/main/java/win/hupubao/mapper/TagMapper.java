package win.hupubao.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import win.hupubao.beans.biz.TagBean;
import win.hupubao.utils.mybatis.MyMapper;

import java.util.List;

@Repository
public interface TagMapper extends MyMapper<TagBean> {


    @Select("select * from tag where id in (select tag_id from article_tag where article_id = #{articleId})")
    List<TagBean> selectTagListByArticleId(@Param("articleId") String articleId);

    List<TagBean> selectList(TagBean tagBean);
}
