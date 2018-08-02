package win.hupubao.core.errors;

import win.hupubao.common.error.ErrorInfo;

public enum CategoryEditError implements ErrorInfo {
    CATAEGORY_EDIT_ERROR("CATAEGORY_EDIT_ERROR", "编辑分类失败");

    public String error_code;
    public String error_msg;

    private CategoryEditError(String error_code, String error_msg) {
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