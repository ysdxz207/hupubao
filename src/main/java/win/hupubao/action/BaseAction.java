package win.hupubao.action;

import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.RequestBody;
import win.hupubao.beans.sys.PageBean;
import win.hupubao.beans.sys.RequestBean;
import win.hupubao.beans.sys.ResponseBean;

/**
 *
 * @author W.feihong
 * @date 2018-07-27 23:12:34
 *
 */
public class BaseAction {

    protected <T> T getEntity(RequestBean requestBean,
                              Class<T> clazz) {
        return JSON.toJavaObject(JSON.parseObject(requestBean.getBizContent()), clazz);
    }

    @SuppressWarnings("unchecked")
    protected <T> PageBean<T> getPageBean(RequestBean requestBean) {
        PageBean<T> pageBean = null;
        try {
            pageBean = getEntity(requestBean, PageBean.class);
        } catch (Exception ignored) {
        }
        if (pageBean == null) {
            pageBean = new PageBean<>();
        }
        pageBean.setService(requestBean.getService());
        return pageBean;
    }

    protected ResponseBean createResponseBean(RequestBean requestBean) {
        return new ResponseBean(requestBean.getService());
    }
}
