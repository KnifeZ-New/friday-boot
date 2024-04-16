package org.knifez.fridaybootadmin.common.annotation.permission;

import cn.dev33.satoken.annotation.SaCheckLogin;

import java.lang.annotation.*;

/**
 * 权限注解 表示允许所有登录后的用户访问
 * 类似PermitAll
 *
@author KnifeZ
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@AllowAuthenticated
@Documented
public @interface AllowAuthenticated {

}
