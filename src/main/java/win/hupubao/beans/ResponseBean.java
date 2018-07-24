package win.hupubao.beans;

import win.hupubao.common.beans.ResponseBase;

import java.io.Serializable;

public class ResponseBean extends ResponseBase<ResponseBean> implements Serializable {

    private static final long serialVersionUID = 2945891986747887468L;
    private String service;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

}
