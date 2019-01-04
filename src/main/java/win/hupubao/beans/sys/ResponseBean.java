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

}
