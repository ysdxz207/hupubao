package win.hupubao.constants;


public class Constants {

    public static final String ENCODING = "UTF-8";
    /**
     * 后台用户session key
     */
    public static final String SESSION_USER_KEY = "fblog_session_admin_user_key";
    public static final int DEFAULT_PAGE_SIZE = 10;
    /**
     * 验证码session key
     */
    public static final String CAPTCHA_SESSION_KEY = "KAPTCHA_SESSION_KEY_FBLOG";

    /**
     * fblog登录cookie key
     */
    public static final String COOKIE_LOGIN_KEY_FBLOG = "COOKIE_LOGIN_KEY_FBLOG";

    /**
     * 跨域允许
     */
    public static String [] ALLOWED_ORIGINS = new String[0];

    public static String DES_KEY = "20151106";
    public static final String PASSWORD_MD5_SALT = "53**42-/!lL~i92.3df*%Z";

}
