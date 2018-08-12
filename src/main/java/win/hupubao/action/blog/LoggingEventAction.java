package win.hupubao.action.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import win.hupubao.action.BaseAction;
import win.hupubao.beans.biz.LoggingEventBean;
import win.hupubao.beans.sys.PageBean;
import win.hupubao.beans.sys.RequestBean;
import win.hupubao.common.utils.LoggerUtils;
import win.hupubao.core.annotation.ServiceInfo;
import win.hupubao.service.LoggingEventService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author W.feihong
 * @date 2018-08-11
 */
@Component("logging")
public class LoggingEventAction extends BaseAction {

    @Autowired
    private LoggingEventService loggingEventService;

    @ServiceInfo(value = "list", permissions = {"logging:view"})
    public String loggingEvents(HttpServletRequest request,
                       HttpServletResponse response,
                       RequestBean requestBean) {

        PageBean<LoggingEventBean> pageBean = getPageBean(requestBean);
        LoggingEventBean loggingEventBean = getEntity(requestBean, LoggingEventBean.class);
        try {
            pageBean = loggingEventService.selectLoggingEventList(loggingEventBean, pageBean);
            pageBean.success();
        } catch (Exception e) {
            LoggerUtils.error("[日志列表]异常", e);
            pageBean.error(e);
        }
        return pageBean.serialize();
    }

}
