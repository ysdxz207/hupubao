package win.hupubao.core.properties;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ysdxz207
 * @date 2018-07-27
 *
 */
@Configuration
@EnableAutoConfiguration
@ConfigurationProperties(prefix = "auth")
public class AuthProperties {

    private List<String> excludes = new ArrayList<>();


    public List<String> getExcludes() {
        return excludes;
    }
}