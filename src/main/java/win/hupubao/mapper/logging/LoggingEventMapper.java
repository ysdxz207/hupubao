package win.hupubao.mapper.logging;

import org.springframework.stereotype.Repository;
import win.hupubao.beans.biz.LoggingEventBean;
import win.hupubao.utils.mybatis.MyMapper;

import java.util.List;

@Repository
public interface LoggingEventMapper extends MyMapper<LoggingEventBean> {

    List<LoggingEventBean> selectList(LoggingEventBean loggingEventBean);
}
