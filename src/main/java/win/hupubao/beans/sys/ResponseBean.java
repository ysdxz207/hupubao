package win.hupubao.beans.sys;

import win.hupubao.common.beans.ResponseBase;

import java.io.Serializable;
/**
 *
 * @author Moses
 * @date 2018-07-4
 * 返回参数
 */
public class ResponseBean extends ResponseBase implements Serializable {

    private static final long serialVersionUID = 2945891986747887468L;
    private String service;
    private String sign;
    private String signType;
    private String randomString;


    public ResponseBean() {
    }

    public ResponseBean(String service) {
        this.service = service;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getRandomString() {
        return randomString;
    }

    public void setRandomString(String randomString) {
        this.randomString = randomString;
    }
}
