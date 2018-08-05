package win.hupubao.beans.biz;

import win.hupubao.domain.Menu;

import javax.persistence.Table;
import java.util.List;

@Table(name = "menu")
public class MenuBean extends Menu {

    private static final long serialVersionUID = -1474436000813768335L;
    private List<MenuBean> children;

    public List<MenuBean> getChildren() {
        return children;
    }

    public void setChildren(List<MenuBean> children) {
        this.children = children;
    }
}
