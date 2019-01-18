package win.hupubao.errors;

import win.hupubao.common.error.ErrorInfo;

public enum UserError implements ErrorInfo {
    USER_EDIT_ERROR("USER_EDIT_ERROR", "编辑用户失败");

    public String error_code;
    public String error_msg;

    private UserError(String error_code, String error_msg) {
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