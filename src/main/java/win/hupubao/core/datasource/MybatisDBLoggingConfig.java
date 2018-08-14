package win.hupubao.core.datasource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import tk.mybatis.spring.annotation.MapperScan;
import win.hupubao.utils.mybatis.MyMapper;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = {"win.hupubao.mapper.logging"},
        sqlSessionFactoryRef = "sqlSessionFactoryLogging",
        markerInterface = MyMapper.class)
public class MybatisDBLoggingConfig {

    @Autowired
    @Qualifier("loggingDataSource")
    private DataSource dataSourceLogging;




    @Bean(name = "loggingDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.logging")
    public DataSource dataSourceLogging() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactoryLogging() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSourceLogging);

        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath*:mybatis/mapper/logging/*.xml"));
        return factoryBean.getObject();

    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplateLogging() throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactoryLogging());
        return template;
    }
}