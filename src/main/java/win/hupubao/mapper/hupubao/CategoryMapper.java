package win.hupubao.mapper.hupubao;

import org.springframework.stereotype.Repository;
import win.hupubao.beans.biz.CategoryBean;
import win.hupubao.utils.mybatis.MyMapper;

import java.util.List;

@Repository
public interface CategoryMapper extends MyMapper<CategoryBean> {


    List<CategoryBean> selectList(CategoryBean categoryBean);
}
