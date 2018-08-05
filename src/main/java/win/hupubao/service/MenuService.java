package win.hupubao.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import win.hupubao.beans.biz.MenuBean;
import win.hupubao.beans.sys.PageBean;
import win.hupubao.domain.Menu;
import win.hupubao.mapper.MenuMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService {

    @Autowired
    private MenuMapper menuMapper;

    public PageBean<MenuBean> selectMenuList(MenuBean menuBean,
                                     PageBean<MenuBean> pageBean) {
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
        List<MenuBean> menuList = menuMapper.select(menuBean);
        pageBean.setList(menuList);
        pageBean.setTotal(menuMapper.selectCount(menuBean));
        return pageBean;
    }

    public List<MenuBean> selectMenuNav(String pid,
                                        String type) {
        MenuBean menuBean = new MenuBean();

        menuBean.setPid(pid);
        if (!"all".equalsIgnoreCase(type)) {
            menuBean.setType(type);
        }
        List<MenuBean> navList = menuMapper.select(menuBean);

        for (MenuBean menuParent : navList) {
            List<MenuBean> children = selectMenuNav(menuParent.getId(),
                    menuParent.getType());
            if (children.size() > 0) {
                menuParent.setChildren(children);
            }
        }
        return navList;
    }

    public List<MenuBean> selectMenuTypeList() {
        return menuMapper.selectMenuTypeList();
    }
}
