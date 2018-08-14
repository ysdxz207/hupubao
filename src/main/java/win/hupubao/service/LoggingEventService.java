package win.hupubao.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import win.hupubao.beans.biz.LoggingEventBean;
import win.hupubao.beans.sys.PageBean;
import win.hupubao.core.datasource.annotations.DataSource;
import win.hupubao.mapper.logging.LoggingEventMapper;

import java.util.List;

/**
 *
 * @author W.feihong
 * @date 2018-08-11
 */

@Service
public class LoggingEventService {

    @Autowired
    private LoggingEventMapper loggingEventMapper;

    @DataSource("loggingDataSource")
    public PageBean<LoggingEventBean> selectLoggingEventList(LoggingEventBean loggingEventBeans,
                                                 PageBean<LoggingEventBean> pageBean) {
        Page page = PageHelper.startPage(pageBean.getPageNum(),
                pageBean.getPageSize(), "timestmp desc");
        List<LoggingEventBean> loggingEventList = loggingEventMapper.selectList(loggingEventBeans);
        pageBean.setList(loggingEventList);
        pageBean.setTotal(page.getTotal());
        return pageBean;
    }

}
