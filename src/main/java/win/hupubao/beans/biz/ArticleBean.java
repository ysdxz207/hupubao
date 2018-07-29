package win.hupubao.beans.biz;

import win.hupubao.domain.Article;

import javax.persistence.Transient;
import java.util.List;

public class ArticleBean extends Article {


    private static final long serialVersionUID = -6575165516738870184L;
    @Transient
    private String category;
    @Transient
    private List<TagBean> tagList;
    @Transient
    private String tagId;//搜索用
    @Transient
    private Integer accessCountAll;
    @Transient
    private Integer accessCountToday;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<TagBean> getTagList() {
        return tagList;
    }

    public void setTagList(List<TagBean> tagList) {
        this.tagList = tagList;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public Integer getAccessCountAll() {
        return accessCountAll;
    }

    public void setAccessCountAll(Integer accessCountAll) {
        this.accessCountAll = accessCountAll;
    }

    public Integer getAccessCountToday() {
        return accessCountToday;
    }

    public void setAccessCountToday(Integer accessCountToday) {
        this.accessCountToday = accessCountToday;
    }
}
