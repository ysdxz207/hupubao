package win.hupubao.beans;

import win.hupubao.annotation.NotNull;

import java.io.Serializable;

public class RequestBean extends Validatable implements Serializable {

    private static final long serialVersionUID = 2813925168595560461L;
    @NotNull
    private String service;
    @NotNull
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
