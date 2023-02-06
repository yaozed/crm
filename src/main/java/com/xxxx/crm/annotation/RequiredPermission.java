package com.xxxx.crm.annotation;

import java.lang.annotation.*;

/**
 * @Auther:姚泽栋
 * @Date:2023/1/11
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequiredPermission {
    //权限码
    String code() default "" ;
}
