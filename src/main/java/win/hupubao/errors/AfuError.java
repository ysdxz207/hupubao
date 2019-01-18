package win.hupubao.errors;

import win.hupubao.common.error.ErrorInfo;

public enum AfuError implements ErrorInfo {
    AFU_EDIT_ERROR("AFU_EDIT_ERROR", "编辑阿福失败"),
    AFU_NOT_EXITS_ERROR("AFU_NOT_EXITS_ERROR", "阿福不存在");

    public String error_code;
    public String error_msg;

    private AfuError(String error_code, String error_msg) {
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