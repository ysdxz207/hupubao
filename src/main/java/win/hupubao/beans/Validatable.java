package win.hupubao.beans;

import com.alibaba.fastjson.JSON;
import win.hupubao.common.error.SystemError;
import win.hupubao.common.error.Throws;
import win.hupubao.core.annotation.NotNull;
import win.hupubao.common.utils.StringUtils;
import win.hupubao.core.exception.ValidationException;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;

/**
 * @author W.feihong
 * @date 2017-08-10
 */
public abstract class Validatable implements Serializable {

    private static final long serialVersionUID = -7731011950843621264L;

    public void validate() throws ValidationException {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();

        Field [] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            NotNull notnull = field.getAnnotation(NotNull.class);
            if (notnull == null) {
                continue;
            }
            String message = notnull.message();

            if (StringUtils.isBlank(message)) {
                message = "Parameter [" + field.getName() + "] should not be null.";
            }

            try {
                if (StringUtils.isBlank(field.get(this))) {
                    map.put(field.getName(), message);
                }
            } catch (IllegalAccessException e) {


            }

        }
        if (!map.isEmpty()) {

            Throws.throwError(SystemError.PARAMETER_ERROR.error_code,
                    StringUtils.join(map.values().
                                    toArray(), ","));
        }
    }
}
