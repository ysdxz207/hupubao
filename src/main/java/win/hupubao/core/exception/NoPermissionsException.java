package win.hupubao.core.exception;

import win.hupubao.common.exception.ThrowsBisinessException;

public class NoPermissionsException extends ThrowsBisinessException {

    public NoPermissionsException() {
    }

    public NoPermissionsException(String message) {
        super(message);
    }

    public NoPermissionsException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoPermissionsException(Throwable cause) {
        super(cause);
    }

    public NoPermissionsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
