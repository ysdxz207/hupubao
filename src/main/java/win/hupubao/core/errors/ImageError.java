package win.hupubao.core.errors;

import win.hupubao.common.error.ErrorInfo;

public enum ImageError implements ErrorInfo {
    IMAGE_UPLOAD_ERROR("IMAGE_UPLOAD_ERROR", "图片上传失败"),
    IMAGE_NOT_EXISTS_ERROR("IMAGE_NOT_EXISTS_ERROR", "图片不存在"),
    IMAGE_DELETE_ERROR("IMAGE_DELETE_ERROR", "图片删除失败");

    public String error_code;
    public String error_msg;

    private ImageError(String error_code, String error_msg) {
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