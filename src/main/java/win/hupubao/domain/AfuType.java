package win.hupubao.domain;

import tk.mybatis.mapper.annotation.KeySql;
import win.hupubao.core.generator.UUIdGenerator;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
public class AfuType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@KeySql(genId = UUIdGenerator.class)
	private String id;
	private String name;
	private String tag;
	private String privateKey;
	private String publicKey;
	private Integer status;
	private Date createTime;


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

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}