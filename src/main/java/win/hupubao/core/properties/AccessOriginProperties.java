package win.hupubao.core.properties;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author W.feihong
 * @date 2018-07-28
 *
 */
@Configuration
@EnableAutoConfiguration
@ConfigurationProperties(prefix = "access.origin")
public class AccessOriginProperties {

    private List<String> allowOrigins = new ArrayList<>();


    public List<String> getAllowOrigins() {
        return allowOrigins;
    }
}