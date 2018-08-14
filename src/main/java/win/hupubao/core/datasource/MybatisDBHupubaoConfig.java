package win.hupubao.core.datasource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import tk.mybatis.spring.annotation.MapperScan;
import win.hupubao.utils.mybatis.MyMapper;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = {"win.hupubao.mapper.hupubao"},
        sqlSessionFactoryRef = "sqlSessionFactoryHupubao",
        markerInterface = MyMapper.class)
public class MybatisDBHupubaoConfig {

    @Autowired
    @Qualifier("hupubaoDataSource")
    private DataSource dataSourceHupubao;

    @Bean(name = "hupubaoDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.hupubao")
    public DataSource dataSourceHupubao() {
        return DataSourceBuilder.create().build();
    }


    @Bean
    @Primary
    public SqlSessionFactory sqlSessionFactoryHupubao() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSourceHupubao);

        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath*:mybatis/mapper/hupubao/*.xml"));

        return factoryBean.getObject();

    }

    @Bean
    @Primary
    public SqlSessionTemplate sqlSessionTemplateHupubao() throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactoryHupubao());
        return template;
    }
}