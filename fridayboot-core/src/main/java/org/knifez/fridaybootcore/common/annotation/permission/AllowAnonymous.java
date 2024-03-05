package org.knifez.fridaybootcore.common.annotation.permission;

import java.lang.annotation.*;

/**
 * 权限注解 表示允许匿名请求
 *
@author KnifeZ
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AllowAnonymous {

}
