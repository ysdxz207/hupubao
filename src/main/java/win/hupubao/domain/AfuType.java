package win.hupubao.domain;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class AfuType implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private String tag;
	private String privateKey;
	private String publicKey;
	private Integer status;


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
}