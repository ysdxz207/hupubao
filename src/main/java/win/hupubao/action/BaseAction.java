package win.hupubao.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import win.hupubao.beans.sys.PageBean;
import win.hupubao.beans.sys.RequestBean;
import win.hupubao.beans.sys.ResponseBean;
import win.hupubao.common.error.SystemError;
import win.hupubao.common.error.Throws;
import win.hupubao.common.utils.rsa.RSA;

import java.util.Map;

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
    protected void verifySign(RequestBean requestBean, String privateKey, String publicKey) {
        JSONObject params = JSON.parseObject(requestBean.getBizContent());
        String sign = requestBean.getSign();
        RSA.RSAKey rsaKey = new RSA.RSAKey(privateKey, publicKey);
        boolean verify = RSA.getInstance().rsaKey(rsaKey)
                .verify(params.toJavaObject(Map.class),
                        sign, RSA.SignType.valueOf(requestBean.getSignType()));
        if (!verify) {
            Throws.throwError(SystemError.SIGN_ERROR);
        }
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
