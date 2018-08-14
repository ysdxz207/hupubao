package win.hupubao.core.datasource;

import win.hupubao.common.utils.LoggerUtils;

public class DataSourceContextHolder {

    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    public static void setDB(String dbType) {
        LoggerUtils.debug("切换到{}数据源", dbType);
        CONTEXT_HOLDER.set(dbType);
    }

    public static String getDB() {
        return (CONTEXT_HOLDER.get());
    }

    public static void clearDB() {
        CONTEXT_HOLDER.remove();
    }
}