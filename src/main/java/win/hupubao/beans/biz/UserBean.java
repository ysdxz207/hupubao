package win.hupubao.beans.biz;

import win.hupubao.domain.User;

import javax.persistence.Transient;

public class UserBean extends User {

    private static final long serialVersionUID = -8798234879062043993L;
    @Transient
    private String roleId;
    @Transient
    private String roleName;
    @Transient
    private String captcha;
    @Transient
    private String rememberMe;
    @Transient
    private String sessionCaptcha;
    @Transient
    private String token;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(String rememberMe) {
        this.rememberMe = rememberMe;
    }

    public String getSessionCaptcha() {
        return sessionCaptcha;
    }

    public void setSessionCaptcha(String sessionCaptcha) {
        this.sessionCaptcha = sessionCaptcha;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
