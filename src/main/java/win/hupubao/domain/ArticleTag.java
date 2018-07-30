package win.hupubao.domain;

import tk.mybatis.mapper.annotation.KeySql;
import win.hupubao.core.generator.IdGenerator;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class ArticleTag implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@KeySql(genId = IdGenerator.class)
	private String id;
	private String articleId;
	private String tagId;


	public String getId (){
		return id;
	}

	public void setId (String id){
		this.id = id;
	}

	public String getArticleId (){
		return articleId;
	}

	public void setArticleId (String articleId){
		this.articleId = articleId;
	}

	public String getTagId (){
		return tagId;
	}

	public void setTagId (String tagId){
		this.tagId = tagId;
	}
}