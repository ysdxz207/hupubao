package win.hupubao.domain;

import javax.persistence.Id;
import java.io.Serializable;

public class LoggingEventException implements Serializable {
    private static final long serialVersionUID = -6705952294817116134L;

    @Id
    private String eventId;
    private Integer i;
    private String traceLine;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Integer getI() {
        return i;
    }

    public void setI(Integer i) {
        this.i = i;
    }

    public String getTraceLine() {
        return traceLine;
    }

    public void setTraceLine(String traceLine) {
        this.traceLine = traceLine;
    }
}
