package win.hupubao.domain;

import javax.persistence.Id;
import java.io.Serializable;

public class LoggingEventProperty implements Serializable {
    private static final long serialVersionUID = -6705952294817116134L;

    @Id
    private String eventId;
    @Id
    private String mappedKey;
    private String mappedValue;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getMappedKey() {
        return mappedKey;
    }

    public void setMappedKey(String mappedKey) {
        this.mappedKey = mappedKey;
    }

    public String getMappedValue() {
        return mappedValue;
    }

    public void setMappedValue(String mappedValue) {
        this.mappedValue = mappedValue;
    }
}
