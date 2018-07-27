package win.hupubao.domain;

import win.hupubao.beans.Validatable;
import win.hupubao.core.annotation.NotNull;

import javax.persistence.*;

/**
 *
 * @author W.feihong
 * @date 2018-07-27
 *
 */
@Entity
public class UserSecurity extends Validatable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select (strftime('%Y%m%d%H%M%s','now','localtime'))")
	private String id;
	private String userId;
	private String privateKey;
	private String publicKey;
	private Long createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
}