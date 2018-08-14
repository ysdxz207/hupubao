package win.hupubao.beans.biz;

import com.alibaba.fastjson.annotation.JSONField;
import win.hupubao.domain.LoggingEvent;
import win.hupubao.utils.CustomDateTimeDeserializer;

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
	@Transient
	@JSONField(deserializeUsing = CustomDateTimeDeserializer.class)
	private Long startTime;
	@Transient
	@JSONField(deserializeUsing = CustomDateTimeDeserializer.class)
	private Long endTime;


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

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
}