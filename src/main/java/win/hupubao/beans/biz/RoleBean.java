package win.hupubao.beans.biz;

import win.hupubao.domain.Role;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Table(name = "role")
public class RoleBean extends Role {
    private static final long serialVersionUID = 7196376022059788146L;

    @Transient
    private String [] permissions;

    public String[] getPermissions() {
        return permissions;
    }

    public void setPermissions(String[] permissions) {
        this.permissions = permissions;
    }
}
