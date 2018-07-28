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

    public PageBean<Menu> selectMenuList(Menu menu,
                                     PageBean<Menu> pageBean) {
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
        List<Menu> menuList = menuMapper.select(menu);
        pageBean.setList(menuList);
        pageBean.setTotal(menuMapper.selectCount(menu));
        return pageBean;
    }

    public List<MenuBean> selectMenuNav(String pid,
                                        String type) {
        Menu menu = new Menu();

        menu.setPid(pid);
        menu.setType(type);
        List<Menu> menuList = menuMapper.select(menu);
        List<MenuBean> navList = new ArrayList<>();

        for (Menu menuParent : menuList) {
            MenuBean parent = new MenuBean();
            BeanUtils.copyProperties(menuParent, parent);
            List<MenuBean> children = selectMenuNav(menuParent.getId(),
                    menuParent.getType());
            if (children.size() > 0) {
                parent.setChildren(children);
            }

            navList.add(parent);
        }
        return navList;
    }

    public List<MenuBean> selectMenuTypeList() {
        return menuMapper.selectMenuTypeList();
    }
}
