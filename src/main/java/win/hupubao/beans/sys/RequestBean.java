package win.hupubao.beans.sys;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
/**
 *
 * @author Moses
 * @date 2018-07-4
 * 请求参数
 */
public class RequestBean implements Serializable {

    private static final long serialVersionUID = 2813925168595560461L;
    private String service;
    private String bizContent;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
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
