package org.knifez.fridaybootcore.common.annotation.permission;

import java.lang.annotation.*;

/**
 * 权限注解 表示允许所有登录后的用户访问
 * 类似PermitAll
 *
@author KnifeZ
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AllowAuthenticated {

}
