package win.hupubao.beans;

import win.hupubao.annotation.NotNull;

import java.io.Serializable;
/**
 *
 * @author Moses
 * @date 2018-07-4
 * 请求参数
 */
public class RequestBean extends Validatable implements Serializable {

    private static final long serialVersionUID = 2813925168595560461L;
    @NotNull
    private String service;
    @NotNull
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
}
