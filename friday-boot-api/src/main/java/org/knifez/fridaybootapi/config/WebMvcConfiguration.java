package org.knifez.fridaybootapi.config;

import org.knifez.fridaybootadmin.interceptor.PermissionInterceptor;
import org.knifez.fridaybootadmin.utils.RedisUtils;
import org.knifez.fridaybootcore.annotation.ApiRestController;
import org.knifez.fridaybootcore.constants.AppConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author KnifeZ
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final RedisUtils redisTemplate;

    public WebMvcConfiguration(RedisUtils redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // 设置api接口统一前缀
        configurer.addPathPrefix(AppConstants.API_PREFIX, c -> c.isAnnotationPresent(ApiRestController.class));
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PermissionInterceptor(redisTemplate)).addPathPatterns(AppConstants.API_PREFIX + "/**")
                .excludePathPatterns("/static/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //添加映射路径
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("*");
    }
}
