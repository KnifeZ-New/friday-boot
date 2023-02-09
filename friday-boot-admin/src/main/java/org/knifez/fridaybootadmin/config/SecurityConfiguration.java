package org.knifez.fridaybootadmin.config;

import lombok.extern.slf4j.Slf4j;
import org.knifez.fridaybootadmin.constants.SecurityConstants;
import org.knifez.fridaybootadmin.exception.JwtAccessDeniedHandler;
import org.knifez.fridaybootadmin.exception.JwtAuthenticationEntryPoint;
import org.knifez.fridaybootadmin.filter.JwtAuthorizationFilter;
import org.knifez.fridaybootadmin.utils.RedisUtils;
import org.knifez.fridaybootcore.annotation.permission.AllowAnonymous;
import org.knifez.fridaybootcore.constants.AppConstants;
import org.knifez.fridaybootcore.utils.AnnotationUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static java.util.Collections.singletonList;

/**
 * @author KnifeZ
 */
@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {


    private final RedisUtils redisUtils;

    private final ResourcePatternResolver resourcePatternResolver;

    public SecurityConfiguration(RedisUtils redisUtils, ResourcePatternResolver resourcePatternResolver) {
        this.redisUtils = redisUtils;
        this.resourcePatternResolver = resourcePatternResolver;
    }

    /**
     * 密码编码器
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //system whitelist
        var whitelist = AnnotationUtils.getAllUrlsByAnnotations(resourcePatternResolver, "classpath:org/knifez/**/controller/**.class", AllowAnonymous.class);
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(whitelist.toArray(new String[0])).permitAll()
                        .requestMatchers(AppConstants.API_PREFIX + "/**").authenticated()
                        .anyRequest().permitAll())
                .addFilter(new JwtAuthorizationFilter(authentication -> authentication, redisUtils))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                .accessDeniedHandler(new JwtAccessDeniedHandler());
        http.headers().frameOptions().disable();
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        org.springframework.web.cors.CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(singletonList("*"));
        configuration.setAllowedHeaders(singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT", "OPTIONS"));
        configuration.setExposedHeaders(singletonList(SecurityConstants.TOKEN_HEADER));
        configuration.setAllowCredentials(false);
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
