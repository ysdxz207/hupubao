package win.hupubao.mapper;

import org.springframework.stereotype.Repository;
import win.hupubao.beans.biz.CategoryBean;
import win.hupubao.domain.Category;
import win.hupubao.utils.mybatis.MyMapper;

import java.util.List;

@Repository
public interface CategoryMapper extends MyMapper<Category> {


    List<CategoryBean> selectList(CategoryBean categoryBean);
}
