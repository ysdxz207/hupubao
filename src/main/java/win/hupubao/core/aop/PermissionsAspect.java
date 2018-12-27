package win.hupubao.core.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import win.hupubao.common.utils.Assert;
import win.hupubao.core.annotation.Logical;
import win.hupubao.core.annotation.ServiceInfo;
import win.hupubao.core.exception.NoPermissionsException;
import win.hupubao.service.UserService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ysdxz207
 * @date 2017-08-27
 */
@Component
@Aspect
public class PermissionsAspect {

    @Autowired
    private UserService userService;

    @Pointcut("execution(public * win.hupubao.service..*.*(..))")
    void serviceMethod() {}




    @Before("serviceMethod() && @annotation(serviceInfo)")
    public void execute(JoinPoint joinPoint,
                        ServiceInfo serviceInfo) {

        if (serviceInfo == null) {
            return;
        }
        Object[] args = joinPoint.getArgs();

        HttpServletRequest request = null;
        for (Object object : args) {
            if (object instanceof HttpServletRequest) {
                request = (HttpServletRequest) object;
                break;
            }
        }

        Assert.notNull(request, "RequiresPermissions注解的方法需要参数HttpServletRequest。");


        String[] permissions = serviceInfo.permissions();
        Logical logical = serviceInfo.logical();


        if (!userService.currentUserHasPermissions(request, permissions, logical)) {
            throw new NoPermissionsException("You do not have this access permission.");
        }

    }
}
