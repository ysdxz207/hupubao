package win.hupubao.core.datasource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import tk.mybatis.mapper.autoconfigure.MybatisProperties;
import tk.mybatis.spring.annotation.MapperScan;
import win.hupubao.utils.mybatis.MyMapper;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties(MybatisProperties.class)
@MapperScan(basePackages = {"win.hupubao.mapper.hupubao"},
        sqlSessionFactoryRef = "sqlSessionFactoryHupubao",
        markerInterface = MyMapper.class)
public class MybatisDBHupubaoConfig {

    @Autowired
    @Qualifier("hupubaoDataSource")
    private DataSource dataSourceHupubao;

    private MybatisProperties mybatisProperties;

    public MybatisDBHupubaoConfig(MybatisProperties mybatisProperties) {
        this.mybatisProperties = mybatisProperties;
    }

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

        factoryBean.setMapperLocations(mybatisProperties.resolveMapperLocations());
        factoryBean.setTypeAliasesPackage(mybatisProperties.getTypeAliasesPackage());
        factoryBean.setConfiguration(mybatisProperties.getConfiguration());

        return factoryBean.getObject();

    }

    @Bean
    @Primary
    public SqlSessionTemplate sqlSessionTemplateHupubao() throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactoryHupubao());
        return template;
    }
}