package win.hupubao.domain;

import tk.mybatis.mapper.annotation.KeySql;
import win.hupubao.core.generator.IdGenerator;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@KeySql(genId = IdGenerator.class)
	private String id;
	private String roleName;
	private String code;
	private Date createTime;


	public String getId (){
		return id;
	}

	public void setId (String id){
		this.id = id;
	}

	public String getRoleName (){
		return roleName;
	}

	public void setRoleName (String roleName){
		this.roleName = roleName;
	}

	public String getCode (){
		return code;
	}

	public void setCode (String code){
		this.code = code;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
