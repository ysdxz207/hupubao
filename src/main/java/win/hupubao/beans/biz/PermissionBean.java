package win.hupubao.beans.biz;

import win.hupubao.domain.Permission;

import javax.persistence.Table;

@Table(name = "permission")
public class PermissionBean extends Permission {
	private static final long serialVersionUID = 1L;

	private String menuName;
	private String pid;
	private Boolean isChecked;

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public Boolean getChecked() {
		return isChecked;
	}

	public void setChecked(Boolean checked) {
		isChecked = checked;
	}
}