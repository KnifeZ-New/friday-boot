package com.knifez.fridaybootadmin.interceptor;

import com.knifez.fridaybootcore.annotation.permission.AllowAuthenticated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class PermissionInterceptor implements HandlerInterceptor {

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
        StringBuffer requestUrl = request.getRequestURL();
        log.info("进入拦截器,用户请求:" + requestUrl);
        if (handler instanceof HandlerMethod handlerMethod) {
            //验证接口权限
            if (handlerMethod.hasMethodAnnotation(AllowAuthenticated.class)) {
                return true;
            }
            // 验证登录信息
            var authentication = SecurityContextHolder.getContext().getAuthentication();
            var authorities = authentication.getAuthorities();
            for (var authority : authorities) {
                authority.getAuthority();
            }
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
