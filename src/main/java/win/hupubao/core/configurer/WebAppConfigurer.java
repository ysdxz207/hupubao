package win.hupubao.core.configurer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import win.hupubao.core.properties.AccessOriginProperties;

@Configuration
@EnableAutoConfiguration
public class WebAppConfigurer implements WebMvcConfigurer {

    @Autowired
    private AccessOriginProperties accessOriginProperties;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(accessOriginProperties.getAllowOrigins())
                .allowedMethods("GET", "POST")
                .allowCredentials(true).maxAge(3600);
    }
}