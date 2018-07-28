package win.hupubao.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import win.hupubao.domain.Permission;
import win.hupubao.utils.mybatis.MyMapper;

import java.util.List;

@Repository
public interface PermissionMapper extends MyMapper<Permission> {

    @Select("select p.permission from role_permission rp " +
            "left join permission p " +
            "on rp.permission_id = p.id where role_id = ${roleId}")
    List<String> selectRolePermissionList(@Param("roleId") String roleId);
}
