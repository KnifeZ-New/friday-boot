package org.knifez.fridaybootapi.config;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.knifez.fridaybootapi.common.filter.NoneCaseSensitiveRequestFilter;
import org.knifez.fridaybootcore.common.constants.AppConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class WebConfig {

    @Value("${spring.jackson.date-format:yyyy-MM-dd HH:mm:ss}")
    private String pattern;

    /**
     * jackson2对象映射器生成器自定义程序
     *
     * @return {@code Jackson2ObjectMapperBuilderCustomizer}
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            //返回时间数据序列化
            builder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(formatter));
            //接收时间数据反序列化
            builder.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(formatter));
        };
    }

    /**
     * 不区分大小写请求筛选器注册
     *
     * @return {@link FilterRegistrationBean}<{@link NoneCaseSensitiveRequestFilter}>
     */
    @Bean
    public FilterRegistrationBean<NoneCaseSensitiveRequestFilter> noneCaseSensitiveRequestFilterRegistration() {
        FilterRegistrationBean<NoneCaseSensitiveRequestFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new NoneCaseSensitiveRequestFilter());
        registrationBean.addUrlPatterns(AppConstants.API_PREFIX.toUpperCase() + "/");
        return registrationBean;

    }

}
