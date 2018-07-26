package win.hupubao.domain;

import win.hupubao.beans.Validatable;
import win.hupubao.core.annotation.NotNull;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 *
 * @author W.feihong
 * @date 2018-07-26
 *
 */
@Entity
public class User extends Validatable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	@NotNull(message = "用户名不可为空")
	private String username;
	private String nickname;
	@NotNull(message = "密码不可为空")
	private String password;
	private Long createTime;
	private Long lastLoginTime;
	private Integer status;
	//////////////////////
	@Transient
	private String roleId;
	@Transient
	private String roleName;
	@Transient
	@NotNull(message = "验证码不可为空")
	private String captcha;
	@Transient
	private String sessionCaptcha;
	@Transient
	private String token;

	public String getId() {
		return id;
	}

	public void setId (String id) {
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

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public String getSessionCaptcha() {
		return sessionCaptcha;
	}

	public void setSessionCaptcha(String sessionCaptcha) {
		this.sessionCaptcha = sessionCaptcha;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}