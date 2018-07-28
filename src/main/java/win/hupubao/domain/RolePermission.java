package win.hupubao.domain;

import tk.mybatis.mapper.annotation.KeySql;
import win.hupubao.core.generator.IdGenerator;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class RolePermission implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@KeySql(genId = IdGenerator.class)
	private String id;
	private String roleId;
	private String permissionId;


	public String getId (){
		return id;
	}

	public void setId (String id){
		this.id = id;
	}

	public String getRoleId (){
		return roleId;
	}

	public void setRoleId (String roleId){
		this.roleId = roleId;
	}

	public String getPermissionId (){
		return permissionId;
	}

	public void setPermissionId (String permissionId){
		this.permissionId = permissionId;
	}
}