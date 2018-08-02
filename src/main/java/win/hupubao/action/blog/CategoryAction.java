package win.hupubao.action.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import win.hupubao.action.BaseAction;
import win.hupubao.beans.biz.CategoryBean;
import win.hupubao.beans.biz.CategoryBean;
import win.hupubao.beans.sys.PageBean;
import win.hupubao.beans.sys.RequestBean;
import win.hupubao.beans.sys.ResponseBean;
import win.hupubao.common.error.SystemError;
import win.hupubao.common.error.Throws;
import win.hupubao.common.utils.LoggerUtils;
import win.hupubao.common.utils.StringUtils;
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
        CategoryBean categoryBean = getEntity(requestBean, CategoryBean.class);
        try {
            pageBean = categoryService.selectCategoryList(categoryBean, pageBean);
            pageBean.success();
        } catch (Exception e) {
            LoggerUtils.error("[分类列表]异常", e);
            pageBean.error(e);
        }
        return pageBean.serialize();
    }

    @ServiceInfo(value = "edit", permissions = {"category:edit"})
    public String edit(HttpServletRequest request,
                       HttpServletResponse response,
                       RequestBean requestBean) {

        ResponseBean responseBean = createResponseBean(requestBean);
        CategoryBean categoryBean = getEntity(requestBean, CategoryBean.class);
        try {

            categoryService.edit(categoryBean);

            responseBean.success();
        } catch (Exception e) {
            LoggerUtils.error("[编辑分类异常][{}]", categoryBean.getId(), e);
            responseBean.error(e);
        }
        return responseBean.serialize();
    }

    @ServiceInfo(value = "delete", permissions = {"category:edit"})
    public String delete(HttpServletRequest request,
                         HttpServletResponse response,
                         RequestBean requestBean) {

        ResponseBean responseBean = createResponseBean(requestBean);
        CategoryBean categoryBean = getEntity(requestBean, CategoryBean.class);
        try {

            if (StringUtils.isEmpty(categoryBean.getId())) {
                Throws.throwError(SystemError.PARAMETER_ERROR);
            }

            categoryService.deleteById(categoryBean.getId());
            responseBean.success();
        } catch (Exception e) {
            responseBean.error(e);
            LoggerUtils.error("[删除分类异常][{}]", categoryBean.getId(), e);
        }
        return responseBean.serialize();
    }
}
