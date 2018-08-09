package win.hupubao.mapper;

import org.springframework.stereotype.Repository;
import win.hupubao.beans.biz.RolePermissionBean;
import win.hupubao.domain.RolePermission;
import win.hupubao.utils.mybatis.MyMapper;

@Repository
public interface RolePermissionMapper extends MyMapper<RolePermissionBean> {

}
