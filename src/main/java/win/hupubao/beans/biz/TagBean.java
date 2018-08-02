package win.hupubao.beans.biz;

import win.hupubao.domain.Tag;

import javax.persistence.Table;
import javax.persistence.Transient;


@Table(name = "tag")
public class TagBean extends Tag {
	private static final long serialVersionUID = 1L;
	@Transient
	private String articleId;

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}
}