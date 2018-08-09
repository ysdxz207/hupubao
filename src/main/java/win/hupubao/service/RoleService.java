package win.hupubao.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import win.hupubao.beans.biz.RoleBean;
import win.hupubao.beans.biz.RolePermissionBean;
import win.hupubao.beans.sys.PageBean;
import win.hupubao.common.error.SystemError;
import win.hupubao.common.error.Throws;
import win.hupubao.common.utils.StringUtils;
import win.hupubao.core.errors.RoleEditError;
import win.hupubao.mapper.PermissionMapper;
import win.hupubao.mapper.RoleMapper;
import win.hupubao.mapper.RolePermissionMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    public PageBean<RoleBean> selectRoleList(RoleBean role,
                                       PageBean<RoleBean> pageBean) {
        Page page = PageHelper.startPage(pageBean.getPageNum(),
                pageBean.getPageSize(), "id desc");
        List<RoleBean> roleList = roleMapper.selectList(role);
        pageBean.setList(roleList);
        pageBean.setTotal(page.getTotal());
        return pageBean;
    }


    @Transactional(rollbackFor = Exception.class)
    public void edit(RoleBean roleBean) {
        int n;

        if (StringUtils.isEmpty(roleBean.getId())) {
            n = roleMapper.insertSelective(roleBean);
        } else {
            if ("1".equals(roleBean.getId())) {
                Throws.throwError(SystemError.SYSTEM_BISINESS_ERROR,
                        "不可编辑[大当家]角色");
            }

            //权限
            //先删除权限
            Example examplePermission = new Example(RolePermissionBean.class);
            Example.Criteria criteria = examplePermission.createCriteria();
            criteria.andEqualTo("roleId", roleBean.getId());
            permissionMapper.deleteByExample(examplePermission);
            //添加权限
            List<RolePermissionBean> permissionList = new ArrayList<>();
            for (String permissionId : roleBean.getPermissions()) {
                RolePermissionBean rolePermissionBean = new RolePermissionBean();
                rolePermissionBean.setRoleId(roleBean.getId());
                rolePermissionBean.setPermissionId(permissionId);
                permissionList.add(rolePermissionBean);
            }
            rolePermissionMapper.insertList(permissionList);

            n = roleMapper.updateByPrimaryKeySelective(roleBean);
        }

        if (n == 0) {
            Throws.throwError(RoleEditError.ROLE_EDIT_ERROR);
        }

    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        if ("1".equals(id)) {
            Throws.throwError(SystemError.SYSTEM_BISINESS_ERROR,
                    "不可删除[大当家]角色");
        }
        roleMapper.deleteByPrimaryKey(id);
    }
}