package com.knifez.fridaybootadmin.interceptor;

import cn.hutool.json.JSONUtil;
import com.knifez.fridaybootcore.annotation.permission.AllowAnonymous;
import com.knifez.fridaybootcore.annotation.permission.AllowAuthenticated;
import com.knifez.fridaybootcore.constants.AppConstants;
import com.knifez.fridaybootcore.enums.ResultStatus;
import com.knifez.fridaybootcore.exception.FridayResultException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class PermissionInterceptor implements HandlerInterceptor {

    private final StringRedisTemplate redisTemplate;

    public PermissionInterceptor(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 前处理
     *
     * @param request  请求
     * @param response 响应
     * @param handler  处理程序
     * @return boolean
     * @throws Exception 异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestUri = request.getRequestURI();
        log.debug("进入拦截器,用户请求:" + requestUri);
        if (handler instanceof HandlerMethod handlerMethod) {
            //验证接口权限
            if (handlerMethod.hasMethodAnnotation(AllowAuthenticated.class) || handlerMethod.hasMethodAnnotation(AllowAnonymous.class)) {
                log.info("跳过鉴权，接口为公共接口:" + requestUri);
                return true;
            }
            // 验证登录信息
            var authentication = SecurityContextHolder.getContext().getAuthentication();
            var permissions = redisTemplate.opsForValue().get(AppConstants.CURRENT_USER_PERMISSION_PREFIX + authentication.getPrincipal().toString());
            var permissionList = JSONUtil.toList(permissions, String.class);
            if (!permissionList.contains(requestUri)) {
                log.warn("鉴权失败，当前用户不具有接口访问权限:" + requestUri);
                throw new FridayResultException(ResultStatus.FORBIDDEN);
            }
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
