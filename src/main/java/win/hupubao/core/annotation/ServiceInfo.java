package win.hupubao.core.annotation;

import java.lang.annotation.*;

/**
 *
 * @author W.feihong
 * @date 2017-08-27
 *
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented//说明该注解将被包含在javadoc中
public @interface ServiceInfo {
    String value();

    String[] permissions() default {};

    Logical logical() default Logical.AND;
}
