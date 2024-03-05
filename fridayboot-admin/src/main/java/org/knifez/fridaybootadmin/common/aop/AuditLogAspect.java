package org.knifez.fridaybootadmin.common.aop;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.json.JSONUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.knifez.fridaybootadmin.entity.AppAuditLog;
import org.knifez.fridaybootadmin.service.IAppAuditLogService;
import org.knifez.fridaybootcore.common.annotation.AuditLog;
import org.knifez.fridaybootcore.dto.FridayResult;
import org.knifez.fridaybootcore.common.enums.ResultStatus;
import org.knifez.fridaybootcore.utils.JwtUtils;
import org.knifez.fridaybootcore.utils.ServletRequestUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.IntStream;

@Aspect
@Component
@Slf4j
public class AuditLogAspect {


    private final IAppAuditLogService auditLogService;
    /**
     * 用于记录操作内容的上下文
     */
    private static final ThreadLocal<String> CONTENT = new ThreadLocal<>();
    /**
     * 用于记录拓展字段的上下文
     */
    private static final ThreadLocal<Map<String, Object>> EXTENDS = new ThreadLocal<>();

    public AuditLogAspect(IAppAuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @Around("@annotation(operation)")
    public Object around(ProceedingJoinPoint joinPoint, Operation operation) throws Throwable {
        // 可能也添加了 @ApiOperation 注解
        AuditLog auditLog = getMethodAnnotation(joinPoint, AuditLog.class);
        return around0(joinPoint, auditLog, operation);
    }

    @Around("!@annotation(io.swagger.v3.oas.annotations.Operation) && @annotation(auditLog)")
    // 兼容处理，只添加 @AuditLog 注解的情况
    public Object around(ProceedingJoinPoint joinPoint, AuditLog auditLog) throws Throwable {
        return around0(joinPoint, auditLog, null);
    }

    private Object around0(ProceedingJoinPoint joinPoint,
                           AuditLog auditLog,
                           Operation operation) throws Throwable {
        // 记录开始时间
        LocalDateTime startTime = LocalDateTime.now();
        try {
            // 执行原有方法
            Object result = joinPoint.proceed();
            // 记录正常执行时的操作日志
            this.log(joinPoint, auditLog, operation, startTime, result, null);
            return result;
        } catch (Throwable exception) {
            this.log(joinPoint, auditLog, operation, startTime, null, exception);
            throw exception;
        } finally {
            clearThreadLocal();
        }
    }

    private static void clearThreadLocal() {
        CONTENT.remove();
        EXTENDS.remove();
    }

    private void log(ProceedingJoinPoint joinPoint,
                     AuditLog auditLog,
                     Operation operation,
                     LocalDateTime startTime, Object result, Throwable exception) {
        try {
            // 判断不记录的情况
            if (!isLogEnable(joinPoint, auditLog)) {
                return;
            }
            // 真正记录操作日志
            this.log0(joinPoint, auditLog, operation, startTime, result, exception);
        } catch (Throwable ex) {
            log.error("[log][记录操作日志时，发生异常，其中参数是 joinPoint({}) auditLog({}) apiOperation({}) result({}) exception({}) ]",
                    joinPoint, auditLog, operation, result, exception, ex);
        }
    }

    private void log0(ProceedingJoinPoint joinPoint, AuditLog auditLog,
                      Operation operation,
                      LocalDateTime startTime, Object result, Throwable exception) {
        AppAuditLog logEntity = new AppAuditLog();
        // 补全通用字段
        logEntity.setExecutionTime(startTime);
        try {
            logEntity.setUserId(JwtUtils.getCurrentUser());
        } catch (Exception e) {
            logEntity.setUserId("");
        }
        // 补全模块信息
//        fillModuleFields(logEntity, joinPoint, auditLog, operation);
        // 补全请求信息
        fillRequestFields(logEntity);
        // 补全方法信息
        fillMethodFields(logEntity, joinPoint, auditLog, startTime, result, exception);

        // 异步记录日志
        auditLogService.save(logEntity);
    }

    private static void fillRequestFields(AppAuditLog logEntity) {
        // 获得 Request 对象
        HttpServletRequest request = ServletRequestUtils.getRequest();
        // 补全请求信息
        logEntity.setActionName(request.getMethod());
        logEntity.setIp(ServletRequestUtils.getRemoteAddr(request));
    }

    private static void fillMethodFields(AppAuditLog logEntity,
                                         ProceedingJoinPoint joinPoint,
                                         AuditLog auditLog,
                                         LocalDateTime startTime, Object result, Throwable exception) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        logEntity.setServiceName(methodSignature.getDeclaringTypeName());
        logEntity.setActionName(methodSignature.getMethod().getDeclaredAnnotation(Operation.class).summary());
        if (auditLog == null || auditLog.logArgs()) {
            logEntity.setParameters(obtainMethodArgs(joinPoint));
        }
        logEntity.setDuration(LocalDateTimeUtil.between(startTime, LocalDateTime.now()).toMillis());
        // （正常）
        if (result instanceof FridayResult<?> fridayResult) {
            logEntity.setResultCode(fridayResult.getCode());
            logEntity.setResultMessage(fridayResult.getMessage());
        } else {
            logEntity.setResultCode(ResultStatus.SUCCESS.getCode());
            logEntity.setResultMessage(ResultStatus.SUCCESS.getMessage());
        }
        // （异常）处理 resultCode 和 resultMsg 字段
        if (exception != null) {
            logEntity.setException(ExceptionUtil.getRootCauseMessage(exception));
            logEntity.setResultCode(500);
        }
    }

    private static String obtainMethodArgs(ProceedingJoinPoint joinPoint) {
        // TODO 提升：参数脱敏和忽略
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] argNames = methodSignature.getParameterNames();
        Object[] argValues = joinPoint.getArgs();
        // 拼接参数
        Map<String, Object> args = new HashMap<>(argValues.length);
        for (int i = 0; i < argNames.length; i++) {
            String argName = argNames[i];
            Object argValue = argValues[i];
            // 被忽略时，标记为 ignore 字符串，避免和 null 混在一起
            args.put(argName, !isIgnoreArgs(argValue) ? argValue : "[ignore]");
        }
        var parameters = JSONUtil.toJsonStr(args);
        if (parameters.length() > 2000) {
            parameters = parameters.substring(0, 2000);
        }
        return parameters;
    }

    private static boolean isLogEnable(ProceedingJoinPoint joinPoint, AuditLog auditLog) {
        // 有 @AuditLog 注解的情况下
        if (auditLog != null) {
            return auditLog.enable();
        }
        // 没有 @ApiOperation 注解的情况下，只记录 POST、PUT、DELETE 的情况
        return obtainFirstLogRequestMethod(obtainRequestMethod(joinPoint)) != null;
    }

    private static RequestMethod obtainFirstLogRequestMethod(RequestMethod[] requestMethods) {
        if (ArrayUtil.isEmpty(requestMethods)) {
            return null;
        }
        return Arrays.stream(requestMethods).filter(requestMethod ->
                        requestMethod == RequestMethod.POST
                                || requestMethod == RequestMethod.PUT
                                || requestMethod == RequestMethod.DELETE)
                .findFirst().orElse(null);
    }

    private static RequestMethod[] obtainRequestMethod(ProceedingJoinPoint joinPoint) {
        RequestMapping requestMapping = AnnotationUtils.getAnnotation( // 使用 Spring 的工具类，可以处理 @RequestMapping 别名注解
                ((MethodSignature) joinPoint.getSignature()).getMethod(), RequestMapping.class);
        return requestMapping != null ? requestMapping.method() : new RequestMethod[]{};
    }

    @SuppressWarnings("SameParameterValue")
    private static <T extends Annotation> T getMethodAnnotation(ProceedingJoinPoint joinPoint, Class<T> annotationClass) {
        return ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(annotationClass);
    }

    private static boolean isIgnoreArgs(Object object) {
        Class<?> clazz = object.getClass();
        // 处理数组的情况
        if (clazz.isArray()) {
            return IntStream.range(0, Array.getLength(object))
                    .anyMatch(index -> isIgnoreArgs(Array.get(object, index)));
        }
        // 递归，处理数组、Collection、Map 的情况
        if (Collection.class.isAssignableFrom(clazz)) {
            return ((Collection<?>) object).stream()
                    .anyMatch((Predicate<Object>) AuditLogAspect::isIgnoreArgs);
        }
        if (Map.class.isAssignableFrom(clazz)) {
            return isIgnoreArgs(((Map<?, ?>) object).values());
        }
        // obj
        return object instanceof MultipartFile
                || object instanceof HttpServletRequest
                || object instanceof HttpServletResponse
                || object instanceof BindingResult;
    }

}
