package win.hupubao.errors;

import win.hupubao.common.error.ErrorInfo;

public enum LoggingEventEditError implements ErrorInfo {
    LOGGING_EVENT_DELETE_ERROR("LOGGING_EVENT_DELETE_ERROR", "删除日志失败");

    public String error_code;
    public String error_msg;

    private LoggingEventEditError(String error_code, String error_msg) {
        this.error_code = error_code;
        this.error_msg = error_msg;
    }

    @Override
    public String getErrorCode() {
        return this.error_code;
    }

    @Override
    public String getErrorMsg() {
        return this.error_msg;
    }
}