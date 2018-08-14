package win.hupubao.mapper.hupubao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import win.hupubao.beans.biz.PermissionBean;
import win.hupubao.domain.Permission;
import win.hupubao.utils.mybatis.MyMapper;

import java.util.List;

@Repository
public interface PermissionMapper extends MyMapper<Permission> {

    List<PermissionBean> selectRolePermissionList(@Param("roleId") String roleId);

    List<PermissionBean> selectList(PermissionBean permissionBean);

    List<PermissionBean> selectPermissionTree();
}
