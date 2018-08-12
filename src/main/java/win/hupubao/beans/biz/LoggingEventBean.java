package win.hupubao.beans.biz;

import win.hupubao.domain.LoggingEvent;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;


@Table(name = "logging_event")
public class LoggingEventBean extends LoggingEvent {
	private static final long serialVersionUID = 1L;

	@Transient
	private List<LoggingEventExceptionBean> exceptionList;
	@Transient
	private List<LoggingEventPropertyBean> propertyList;

	@Transient
	private String exception;


	public List<LoggingEventExceptionBean> getExceptionList() {
		return exceptionList;
	}

	public void setExceptionList(List<LoggingEventExceptionBean> exceptionList) {
		this.exceptionList = exceptionList;
	}

	public List<LoggingEventPropertyBean> getPropertyList() {
		return propertyList;
	}

	public void setPropertyList(List<LoggingEventPropertyBean> propertyList) {
		this.propertyList = propertyList;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}
}