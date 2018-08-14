package win.hupubao.mapper.hupubao;

import org.springframework.stereotype.Repository;
import win.hupubao.beans.biz.RoleBean;
import win.hupubao.utils.mybatis.MyMapper;

import java.util.List;

@Repository
public interface RoleMapper extends MyMapper<RoleBean> {

    List<RoleBean> selectList(RoleBean roleBean);
}
