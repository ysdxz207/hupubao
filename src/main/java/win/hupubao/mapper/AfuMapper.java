package win.hupubao.mapper;

import org.springframework.stereotype.Repository;
import win.hupubao.beans.biz.AfuBean;
import win.hupubao.utils.mybatis.MyMapper;

import java.util.List;

@Repository
public interface AfuMapper extends MyMapper<AfuBean> {

    List<AfuBean> selectList(AfuBean afuBean);
}
