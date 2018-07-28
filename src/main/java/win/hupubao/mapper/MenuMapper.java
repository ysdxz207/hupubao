package win.hupubao.mapper;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import win.hupubao.beans.biz.MenuBean;
import win.hupubao.domain.Menu;
import win.hupubao.utils.mybatis.MyMapper;

import java.util.List;

@Repository
public interface MenuMapper extends MyMapper<Menu> {


    @Select("select * from menu where pid='root' group by type")
    List<MenuBean> selectMenuTypeList();
}
