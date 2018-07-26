package win.hupubao.service;

import org.springframework.stereotype.Service;
import win.hupubao.beans.RequestBean;
import win.hupubao.beans.ResponseBean;
import win.hupubao.common.utils.Captcha;
import win.hupubao.constants.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author W.feihong
 * @date 2018-07-25
 * 验证码服务
 */

@Service
public class CaptchaService extends BaseService{


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
