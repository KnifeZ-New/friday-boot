package org.knifez.fridaybootadmin.common.annotation.permission;

import cn.dev33.satoken.annotation.SaIgnore;

import java.lang.annotation.*;

/**
 * 权限注解 表示允许匿名请求
 *
 * @author KnifeZ
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@SaIgnore
@Documented
public @interface AllowAnonymous {

}
