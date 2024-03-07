package org.knifez.fridaybootadmin.config.security;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.knifez.fridaybootadmin.utils.JwtTokenUtils;
import org.knifez.fridaybootadmin.utils.RedisUtils;
import org.knifez.fridaybootcore.common.constants.AppConstants;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

/**
 * 鉴权过滤器
 *
 * @author KnifeZ
 */
@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final StringRedisTemplate redisTemplate;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, StringRedisTemplate redisTemplate) {
        super(authenticationManager);
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("call " + request.getRequestURI());
        String token = request.getHeader(AppConstants.JWT_TOKEN_HEADER);
        if (token == null || !token.startsWith(AppConstants.JWT_TOKEN_PREFIX)) {
            SecurityContextHolder.clearContext();
            chain.doFilter(request, response);
            return;
        }
        try {
            // 验证是否过期token
            if (JwtTokenUtils.isExpired(token) || RedisUtils.getUserToken(redisTemplate, JwtTokenUtils.getCurrentUser()).stream().noneMatch(x -> x.equals(token))) {
                SecurityContextHolder.clearContext();
                chain.doFilter(request, response);
                return;
            }
            var authorities = RedisUtils.getStr(redisTemplate, AppConstants.CURRENT_USER_PERMISSION_PREFIX + JwtTokenUtils.getCurrentUser());
            JwtTokenUtils.setSecurityContextAuthentication(token, authorities);
        } catch (JwtException e) {
            logger.warn("Invalid jwt : " + e.getMessage());
        }

        chain.doFilter(request, response);
    }
}
