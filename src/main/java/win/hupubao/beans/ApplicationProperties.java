package win.hupubao.beans;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "spring")
public class ApplicationProperties {
    private Map<String, String> datasource;

    public void setDatasource(Map<String, String> datasource) {
        this.datasource = datasource;
    }

    public Map<String, String> getDatasource() {
        return datasource;
    }
}
