package win.hupubao.beans.biz;

import win.hupubao.domain.Permission;

import javax.persistence.Table;
import java.util.List;

@Table(name = "permission")
public class PermissionBean extends Permission {
	private static final long serialVersionUID = 1L;

	private Boolean checked;
	private List<PermissionBean> children;

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}


	public List<PermissionBean> getChildren() {
		return children;
	}

	public void setChildren(List<PermissionBean> children) {
		this.children = children;
	}
}
