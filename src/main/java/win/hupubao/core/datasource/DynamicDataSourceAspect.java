package win.hupubao.core.datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import win.hupubao.core.datasource.annotations.DataSource;

@Aspect
@Component
public class DynamicDataSourceAspect {

    @Before("@annotation(ds)")
    public void beforeSwitchDS(JoinPoint point,
                               DataSource ds){
        DataSourceContextHolder.setDB(ds.value());
    }


    @After("@annotation(ds)")
    public void afterSwitchDS(JoinPoint point,
                              DataSource ds){

        DataSourceContextHolder.clearDB();

    }

}