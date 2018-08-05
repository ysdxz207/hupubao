package win.hupubao.mapper;

import org.springframework.stereotype.Repository;
import win.hupubao.beans.biz.UserBean;
import win.hupubao.domain.User;
import win.hupubao.utils.mybatis.MyMapper;

import java.util.List;

@Repository
public interface UserMapper extends MyMapper<User> {


    List<UserBean> selectList(UserBean userBean);
}
