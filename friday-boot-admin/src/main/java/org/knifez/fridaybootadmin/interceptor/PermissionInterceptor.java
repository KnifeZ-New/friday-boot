package org.knifez.fridaybootadmin.interceptor;

import cn.hutool.json.JSONUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.knifez.fridaybootadmin.utils.RedisUtils;
import org.knifez.fridaybootcore.annotation.permission.AllowAnonymous;
import org.knifez.fridaybootcore.annotation.permission.AllowAuthenticated;
import org.knifez.fridaybootcore.constants.AppConstants;
import org.knifez.fridaybootcore.enums.ResultStatus;
import org.knifez.fridaybootcore.exception.FridayResultException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class PermissionInterceptor implements HandlerInterceptor {

    private final RedisUtils redisTemplate;

    public PermissionInterceptor(RedisUtils redisTemplate) {
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
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        String requestUri = (request.getRequestURI() + ":" + request.getMethod()).toLowerCase();
        log.debug("进入拦截器,用户请求:" + requestUri);
        if (handler instanceof HandlerMethod handlerMethod) {
            //验证接口权限
            if (handlerMethod.hasMethodAnnotation(AllowAuthenticated.class)
                    || handlerMethod.hasMethodAnnotation(AllowAnonymous.class)
                    || handlerMethod.getBean().getClass().getAnnotation(AllowAuthenticated.class) != null
                    || handlerMethod.getBean().getClass().getAnnotation(AllowAnonymous.class) != null) {
                log.info("跳过鉴权，接口为公共接口:" + requestUri);
                return true;
            }
            // 验证登录信息
            var authentication = SecurityContextHolder.getContext().getAuthentication();
            var permissions = redisTemplate.get(AppConstants.CURRENT_USER_PERMISSION_PREFIX + authentication.getPrincipal().toString());
            var permissionList = JSONUtil.toList(permissions, String.class);
            if (!permissionList.contains(requestUri)) {
                log.warn("鉴权失败，当前用户不具有接口访问权限:" + requestUri);
                throw new FridayResultException(ResultStatus.FORBIDDEN);
            }
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
