package win.hupubao.core.properties;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ysdxz207
 * @date 2018-07-28
 *
 */
@Configuration
@EnableAutoConfiguration
@ConfigurationProperties(prefix = "access.origin")
public class AccessOriginProperties {

    private String [] allowOrigins = {};


    public String [] getAllowOrigins() {
        return allowOrigins;
    }

    public void setAllowOrigins(String[] allowOrigins) {
        this.allowOrigins = allowOrigins;
    }
}