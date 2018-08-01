package win.hupubao.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "spring")
public class SpringProperties {
    private Map<String, String> datasource;
    private String domain;

    public void setDatasource(Map<String, String> datasource) {
        this.datasource = datasource;
    }

    public Map<String, String> getDatasource() {
        return datasource;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
