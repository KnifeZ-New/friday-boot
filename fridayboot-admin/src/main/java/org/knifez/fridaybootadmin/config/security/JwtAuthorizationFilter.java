package org.knifez.fridaybootadmin.config.security;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.knifez.fridaybootadmin.utils.JwtTokenUtils;
import org.knifez.fridaybootcore.common.constants.AppConstants;
import org.knifez.fridaybootcore.utils.JwtUtils;
import org.knifez.fridaybootcore.utils.RedisUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
        String tokenValue = token.replace(AppConstants.JWT_TOKEN_PREFIX, "");
        UsernamePasswordAuthenticationToken authenticationToken = null;
        try {
            String previousToken = redisTemplate.opsForValue().get(RedisUtils.formatKey(JwtUtils.getCurrentUser()));
            if (!token.equals(previousToken)) {
                SecurityContextHolder.clearContext();
                chain.doFilter(request, response);
                return;
            }
            authenticationToken = JwtTokenUtils.getAuthentication(tokenValue);
        } catch (JwtException e) {
            logger.warn("Invalid jwt : " + e.getMessage());
        }

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }
}
