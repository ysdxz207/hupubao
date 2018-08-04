package win.hupubao.domain;

import com.alibaba.fastjson.annotation.JSONField;
import tk.mybatis.mapper.annotation.KeySql;
import win.hupubao.core.generator.IdGenerator;
import win.hupubao.utils.CustomDateTimeSerializer;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 *
 * @author W.feihong
 * @date 2018-07-26
 *
 */
@Entity
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@KeySql(genId = IdGenerator.class)
	private String id;
	private String username;
	private String nickname;
	private String password;
	@JSONField(serializeUsing = CustomDateTimeSerializer.class)
	private Long createTime;
	@JSONField(serializeUsing = CustomDateTimeSerializer.class)
	private Long lastLoginTime;
	private Integer status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Long lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}