package win.hupubao.errors;

import win.hupubao.common.error.ErrorInfo;

public enum LoginError implements ErrorInfo {
    NO_USERNAME_ERROR("NO_USERNAME_ERROR", "用户名为空"),
    NO_PASSWORD_ERROR("NO_PASSWORD_ERROR", "密码为空"),
    NO_CAPTCHA_ERROR("NO_CAPTCHA_ERROR", "验证码为空"),
    WRONG_CAPTCHA_ERROR("WRONG_CAPTCHA_ERROR", "验证码错误"),
    WRONG_USERNAME_OR_PASSWORD_ERROR("WRONG_USERNAME_OR_PASSWORD_ERROR", "用户名或密码错误"),
    NO_AUTH_ERROR("NO_AUTH_ERROR", "未登录");

    public String error_code;
    public String error_msg;

    private LoginError(String error_code, String error_msg) {
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