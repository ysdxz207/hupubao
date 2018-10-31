package win.hupubao.core.exception;

import win.hupubao.common.exception.BusinessException;

/**
 * @author Moses
 * @date 2017-08-11
 */
public class ValidationException extends BusinessException {

    private static final long serialVersionUID = 6776477720065127558L;

    public ValidationException(String message) {
        super(message);
    }
}
