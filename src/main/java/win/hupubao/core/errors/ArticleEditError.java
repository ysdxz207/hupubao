package win.hupubao.core.errors;

import win.hupubao.common.error.ErrorInfo;

public enum ArticleEditError implements ErrorInfo {
    ARTICLE_EDIT_ERROR("ARTICLE_EDIT_ERROR", "编辑文章失败"),
    ARTICLE_DELETE_ERROR("ARTICLE_DELETE_ERROR", "文章删除失败");

    public String error_code;
    public String error_msg;

    private ArticleEditError(String error_code, String error_msg) {
        this.error_code = error_code;
        this.error_msg = error_msg;
    }

    public String getErrorCode() {
        return this.error_code;
    }

    public String getErrorMsg() {
        return this.error_msg;
    }
}