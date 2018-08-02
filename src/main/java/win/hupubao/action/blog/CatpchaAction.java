package win.hupubao.action.blog;

import org.springframework.stereotype.Component;
import win.hupubao.action.BaseAction;
import win.hupubao.beans.sys.RequestBean;
import win.hupubao.beans.sys.ResponseBean;
import win.hupubao.common.utils.Captcha;
import win.hupubao.constants.Constants;
import win.hupubao.core.annotation.ServiceInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component("captcha")
public class CatpchaAction extends BaseAction {

    @ServiceInfo(value = "create")
    public ResponseBean create(HttpServletRequest request,
                               HttpServletResponse response,
                               RequestBean requestBean) {
        ResponseBean responseBean = new ResponseBean(requestBean.getService());

        try {
            Captcha.CaptchaImage captchaImage = Captcha.getInstance()
                    .generate();
            request.getSession().setAttribute(Constants.CAPTCHA_SESSION_KEY, captchaImage.getCaptchaCode());
            responseBean.success(captchaImage.getBase64Image());
        } catch (Exception e) {
            responseBean.error(e);
            e.printStackTrace();
        }
        return responseBean;
    }
}
