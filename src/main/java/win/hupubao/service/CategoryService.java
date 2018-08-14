package win.hupubao.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import win.hupubao.beans.biz.CategoryBean;
import win.hupubao.beans.sys.PageBean;
import win.hupubao.common.error.Throws;
import win.hupubao.common.utils.StringUtils;
import win.hupubao.core.errors.CategoryEditError;
import win.hupubao.mapper.hupubao.CategoryMapper;

import java.util.List;

/**
 *
 * @author W.feihong
 * @date 2018-08-02
 * 文章分类
 */

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    public PageBean<CategoryBean> selectCategoryList(CategoryBean categoryBeans,
                                                 PageBean<CategoryBean> pageBean) {
        Page page = PageHelper.startPage(pageBean.getPageNum(),
                pageBean.getPageSize(), "create_time desc");
        List<CategoryBean> categoryList = categoryMapper.selectList(categoryBeans);
        pageBean.setList(categoryList);
        pageBean.setTotal(page.getTotal());
        return pageBean;
    }

    @Transactional(rollbackFor = Exception.class)
    public void edit(CategoryBean categoryBean) {
        int n = 0;

        if (StringUtils.isEmpty(categoryBean.getId())) {
            n = categoryMapper.insertSelective(categoryBean);
        } else {
            n = categoryMapper.updateByPrimaryKeySelective(categoryBean);
        }

        if (n == 0) {
            Throws.throwError(CategoryEditError.CATAEGORY_EDIT_ERROR);
        }

    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        categoryMapper.deleteByPrimaryKey(id);
    }
}
