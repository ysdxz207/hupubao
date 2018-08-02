package win.hupubao.action.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import win.hupubao.action.BaseAction;
import win.hupubao.beans.sys.PageBean;
import win.hupubao.beans.sys.RequestBean;
import win.hupubao.beans.sys.ResponseBean;
import win.hupubao.common.error.Throws;
import win.hupubao.core.annotation.ServiceInfo;
import win.hupubao.domain.Image;
import win.hupubao.service.ImageService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author W.feihong
 * @date 2018-07-31
 * 图片
 */
@Component("image")
public class ImageAction extends BaseAction {


    @Autowired
    private ImageService imageService;

    @ServiceInfo(value = "list", permissions = {"image:view"})
    public String images(HttpServletRequest request,
                       HttpServletResponse response,
                       RequestBean requestBean) {

        PageBean<Image> pageBean = getPageBean(requestBean);
        try {
            Image image = getEntity(requestBean, Image.class);
            pageBean = imageService.selectByPage(image, pageBean);
            pageBean.success();
        } catch (Exception e) {
            pageBean.error(e);
        }
        return pageBean.serialize();
    }

    @ServiceInfo(value = "upload", permissions = {"image:edit"})
    public String upload(HttpServletRequest request,
                         HttpServletResponse response,
                         RequestBean requestBean) {

        ResponseBean responseBean = createResponseBean(requestBean);

        try {
            Image image = getEntity(requestBean, Image.class);
            imageService.upload(image);
            responseBean.success(image);
        } catch (Exception e) {
            responseBean.error(e);
        }
        return responseBean.serialize();
    }

    @ServiceInfo(value = "delete", permissions = {"image:delete"})
    public String delete(HttpServletRequest request,
                         HttpServletResponse response,
                         RequestBean requestBean) {

        ResponseBean responseBean = createResponseBean(requestBean);

        try {
            Image image = getEntity(requestBean, Image.class);
            if (image.getId() == null) {
                Throws.throwError("Parameter [id] should not be null.");
            }
            imageService.delete(image.getId());
            responseBean.success();
        } catch (Exception e) {
            responseBean.error(e);
        }
        return responseBean.serialize();
    }

}
