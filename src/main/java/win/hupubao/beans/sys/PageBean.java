package win.hupubao.beans.sys;

import win.hupubao.common.beans.ResponseBase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Moses
 * @date 2018-07-4
 * 返回分页参数
 */
public class PageBean<E> extends ResponseBase<PageBean> implements Serializable {

    private static final long serialVersionUID = 2945891986747887468L;
    private String service;
    private List<E> list = new ArrayList<>();

    private int pageNum = 1;
    private int pageSize = 10;
    private long total;

    public PageBean() {
    }

    public PageBean(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public PageBean(String service) {
        this.service = service;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public List<E> getList() {
        return list;
    }

    public void setList(List<E> list) {
        this.list = list;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
