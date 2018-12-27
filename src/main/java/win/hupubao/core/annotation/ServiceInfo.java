package win.hupubao.core.annotation;

import java.lang.annotation.*;

/**
 *
 * @author ysdxz207
 * @date 2017-08-27
 *
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented//说明该注解将被包含在javadoc中
@Inherited// 支持继承，这样在aop的cglib动态代理类中才能获取到注解
public @interface ServiceInfo {
    String value();

    String[] permissions() default {};

    Logical logical() default Logical.AND;
}
