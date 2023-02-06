package com.xxxx.crm.aspect;

import com.xxxx.crm.annotation.RequiredPermission;
import com.xxxx.crm.exceptions.AuthException;
import jdk.nashorn.internal.objects.AccessorPropertyDescriptor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Auther:姚泽栋
 * @Date:2023/1/11
 */
@Component
@Aspect // AOP
public class PermissionProxy {
    @Resource
    private HttpSession session;

    @Around(value = "@annotation(com.xxxx.crm.annotation.RequiredPermission)")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Object result = null;
        List<String> permissions = (List<String>) session.getAttribute("permissions");
    if(null ==permissions || permissions.size() <1){
        //抛异常
        throw new AuthException();
    }
    MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
    RequiredPermission requiredPermission = methodSignature.getMethod().getAnnotation(RequiredPermission.class);
    if(!(permissions.contains(requiredPermission.code()))){
        throw new AuthException();

    }
        result = pjp.proceed();
        return result;
    }

}
