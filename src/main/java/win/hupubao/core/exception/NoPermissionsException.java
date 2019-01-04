package win.hupubao.core.exception;


import win.hupubao.common.error.SystemError;
import win.hupubao.common.exception.BusinessException;

public class NoPermissionsException extends BusinessException {


    public NoPermissionsException(String message) {
        super(SystemError.SYSTEM_BISINESS_ERROR.error_code, message);
    }

}
