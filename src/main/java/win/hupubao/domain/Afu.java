package win.hupubao.domain;

import tk.mybatis.mapper.annotation.KeySql;
import win.hupubao.core.generator.IdGenerator;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Afu implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@KeySql(genId = IdGenerator.class)
	private String id;
	private String name;
	private String type;
	private Date createTime;
	private String content;

	//
	@Transient
	private String typeName;


	public String getId (){
		return id;
	}

	public void setId (String id){
		this.id = id;
	}

	public String getName (){
		return name;
	}

	public void setName (String name){
		this.name = name;
	}

	public String getType (){
		return type;
	}

	public void setType (String type){
		this.type = type;
	}

	public Date getCreateTime (){
		return createTime;
	}

	public void setCreateTime (Date createTime){
		this.createTime = createTime;
	}

	public String getContent (){
		return content;
	}

	public void setContent (String content){
		this.content = content;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}