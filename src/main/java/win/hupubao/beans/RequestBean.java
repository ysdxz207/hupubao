package win.hupubao.beans;

import java.io.Serializable;

public class RequestBean implements Serializable {

    private static final long serialVersionUID = 2813925168595560461L;
    private String service;
    private String data;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
