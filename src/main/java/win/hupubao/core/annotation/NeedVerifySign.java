package win.hupubao.core.annotation;

import java.lang.annotation.*;

/**
 *
 * @author ysdxz207
 * @date 2018-12-27 10:14:52
 * 需要验签注解，在action上使用
 *
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Inherited// 支持继承，这样在aop的cglib动态代理类中才能获取到注解
public @interface NeedVerifySign {
}