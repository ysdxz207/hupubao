package win.hupubao.beans.sys;

import com.alibaba.fastjson.JSON;
import win.hupubao.beans.biz.AfuTypeBean;

import java.io.Serializable;
/**
 *
 * @author Moses
 * @date 2018-07-4
 * 请求参数
 */
public class RequestBean implements Serializable {

    private static final long serialVersionUID = 2813925168595560461L;

    private String afuType;
    private String service;
    private String sign;
    private String signType;
    private String randomString;
    private String bizContent;

    private AfuTypeBean afuTypeBean;


    public String getAfuType() {
        return afuType;
    }

    public void setAfuType(String afuType) {
        this.afuType = afuType;
    }

    public AfuTypeBean getAfuTypeBean() {
        return afuTypeBean;
    }

    public void setAfuTypeBean(AfuTypeBean afuTypeBean) {
        this.afuTypeBean = afuTypeBean;
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

    public String getBizContent() {
        return bizContent;
    }

    public void setBizContent(String bizContent) {
        this.bizContent = bizContent;
    }

    public <T> T toEntity(Class<T> clazz) {
        return JSON.toJavaObject(JSON.parseObject(this.bizContent), clazz);
    }
}
