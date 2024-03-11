package org.knifez.fridaybootcore.common.annotation;

import java.lang.annotation.*;

/**
 * 审计日志
 *
 * @author KnifeZ
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuditLog {
    /**
     * 是否记录操作日志
     */
    boolean enable() default true;

    /**
     * 是否记录方法参数
     */
    boolean logArgs() default false;

}
