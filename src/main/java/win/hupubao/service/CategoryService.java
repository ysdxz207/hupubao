package win.hupubao.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import win.hupubao.beans.biz.CategoryBean;
import win.hupubao.beans.sys.PageBean;
import win.hupubao.domain.Category;
import win.hupubao.mapper.CategoryMapper;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    public PageBean<CategoryBean> selectCategoryList(CategoryBean categoryBeans,
                                                 PageBean<CategoryBean> pageBean) {
        Page page = PageHelper.startPage(pageBean.getPageNum(),
                pageBean.getPageSize(), "name desc");
        List<CategoryBean> categoryList = categoryMapper.selectList(categoryBeans);
        pageBean.setList(categoryList);
        pageBean.setTotal(page.getTotal());
        return pageBean;
    }
}
