package win.hupubao.domain;

import tk.mybatis.mapper.annotation.KeySql;
import win.hupubao.core.generator.IdGenerator;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Permission implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@KeySql(genId = IdGenerator.class)
	private String id;
	private String menuId;
	private String permissionName;
	private String permission;


	public String getId (){
		return id;
	}

	public void setId (String id){
		this.id = id;
	}

	public String getMenuId (){
		return menuId;
	}

	public void setMenuId (String menuId){
		this.menuId = menuId;
	}

	public String getPermissionName (){
		return permissionName;
	}

	public void setPermissionName (String permissionName){
		this.permissionName = permissionName;
	}

	public String getPermission (){
		return permission;
	}

	public void setPermission (String permission){
		this.permission = permission;
	}
}