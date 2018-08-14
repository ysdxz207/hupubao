package win.hupubao.mapper.hupubao;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import win.hupubao.beans.biz.MenuBean;
import win.hupubao.utils.mybatis.MyMapper;

import java.util.List;

@Repository
public interface MenuMapper extends MyMapper<MenuBean> {


    @Select("select * from menu where pid='root' group by type")
    List<MenuBean> selectMenuTypeList();

}
