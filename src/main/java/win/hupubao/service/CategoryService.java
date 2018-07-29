package win.hupubao.service;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import win.hupubao.beans.sys.PageBean;
import win.hupubao.domain.Category;
import win.hupubao.mapper.CategoryMapper;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    public PageBean<Category> selectCategoryList(Category category,
                                                 PageBean<Category> pageBean) {
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
        List<Category> categoryList = categoryMapper.select(category);
        pageBean.setList(categoryList);
        pageBean.setTotal(categoryMapper.selectCount(category));
        return pageBean;
    }
}
