package com.knifez.fridayboot.config;

import com.knifez.fridayboot.annotation.ApiRestController;
import org.springframework.context.annotation.Configuration;
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
        configurer.addPathPrefix("api", c -> c.isAnnotationPresent(ApiRestController.class));
    }
}
