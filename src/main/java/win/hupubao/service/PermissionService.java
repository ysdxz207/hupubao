package win.hupubao.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import win.hupubao.beans.biz.PermissionBean;
import win.hupubao.beans.sys.PageBean;
import win.hupubao.common.error.Throws;
import win.hupubao.common.utils.StringUtils;
import win.hupubao.core.errors.PermissionEditError;
import win.hupubao.mapper.PermissionMapper;

import java.util.List;

@Service
public class PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    public PageBean<PermissionBean> selectPermissionList(PermissionBean permission,
                                       PageBean<PermissionBean> pageBean) {
        Page page = PageHelper.startPage(pageBean.getPageNum(),
                pageBean.getPageSize(), "create_time desc");
        List<PermissionBean> permissionList = permissionMapper.selectList(permission);
        pageBean.setList(permissionList);
        pageBean.setTotal(page.getTotal());
        return pageBean;
    }


    @Transactional(rollbackFor = Exception.class)
    public void edit(PermissionBean permissionBean) {
        int n;

        if (StringUtils.isEmpty(permissionBean.getId())) {
            n = permissionMapper.insertSelective(permissionBean);
        } else {
            n = permissionMapper.updateByPrimaryKeySelective(permissionBean);
        }

        if (n == 0) {
            Throws.throwError(PermissionEditError.PERMISSION_EDIT_ERROR);
        }

    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        permissionMapper.deleteByPrimaryKey(id);
    }

    public List<PermissionBean> selectRolePermissionList(String roleId) {
        return permissionMapper.selectValidatePermissionList(roleId);
    }
}