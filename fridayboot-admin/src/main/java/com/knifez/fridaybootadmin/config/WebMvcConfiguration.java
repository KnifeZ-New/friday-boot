package com.knifez.fridaybootadmin.config;

import com.knifez.fridaybootadmin.interceptor.PermissionInterceptor;
import com.knifez.fridaybootcore.annotation.ApiRestController;
import com.knifez.fridaybootcore.constants.FridaybootAppConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * @author zhang
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // 设置api接口统一前缀
        configurer.addPathPrefix(FridaybootAppConstants.API_PREFIX, c -> c.isAnnotationPresent(ApiRestController.class));
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PermissionInterceptor()).addPathPatterns("/api/**");
    }
}
