package win.hupubao.action.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import win.hupubao.action.BaseAction;
import win.hupubao.beans.biz.CategoryBean;
import win.hupubao.beans.sys.PageBean;
import win.hupubao.beans.sys.RequestBean;
import win.hupubao.core.annotation.ServiceInfo;
import win.hupubao.domain.Category;
import win.hupubao.service.CategoryService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author W.feihong
 * @date 2018-07-29
 * 文章分类
 */
@Component("category")
public class CategoryAction extends BaseAction {

    @Autowired
    private CategoryService categoryService;

    @ServiceInfo(value = "list", permissions = {"category:view"})
    public String categorys(HttpServletRequest request,
                       HttpServletResponse response,
                       RequestBean requestBean) {

        PageBean<CategoryBean> pageBean = getPageBean(requestBean);
        try {
            CategoryBean categoryBean = getEntity(requestBean, CategoryBean.class);
            pageBean = categoryService.selectCategoryList(categoryBean, pageBean);
            pageBean.success();
        } catch (Exception e) {
            pageBean.error(e);
        }
        return pageBean.serialize();
    }
}
