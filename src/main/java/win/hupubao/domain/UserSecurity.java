package win.hupubao.domain;

import tk.mybatis.mapper.annotation.KeySql;
import win.hupubao.core.generator.IdGenerator;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author ysdxz207
 * @date 2018-07-27
 *
 */
@Entity
public class UserSecurity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@KeySql(genId = IdGenerator.class)
	private String id;
	private String userId;
	private String desKey;
	private Date createTime;

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

	public String getDesKey() {
		return desKey;
	}

	public void setDesKey(String desKey) {
		this.desKey = desKey;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}