package win.hupubao.core.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import win.hupubao.common.utils.LoggerUtils;

public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        LoggerUtils.debug("数据源为{}", DataSourceContextHolder.getDB());

        return DataSourceContextHolder.getDB();
    }

}