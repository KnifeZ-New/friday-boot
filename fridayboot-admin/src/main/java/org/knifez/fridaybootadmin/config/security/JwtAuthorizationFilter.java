package org.knifez.fridaybootadmin.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.knifez.fridaybootadmin.utils.JwtTokenUtils;
import org.knifez.fridaybootadmin.utils.RedisUtils;
import org.knifez.fridaybootcore.common.constants.AppConstants;
import org.knifez.fridaybootcore.common.enums.ResultStatus;
import org.knifez.fridaybootcore.common.exception.FridayResultException;
import org.knifez.fridaybootcore.dto.FridayResult;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.io.PrintWriter;

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
        } else {
            try {
                // 验证是否过期token
                if (JwtTokenUtils.isExpired(token) || RedisUtils.getUserToken(redisTemplate, JwtTokenUtils.getCurrentUser()).stream().noneMatch(x -> x.equals(token))) {
                    SecurityContextHolder.clearContext();
                } else {
                    var userInfo = RedisUtils.getStr(redisTemplate, AppConstants.CURRENT_USERINFO_DTO + JwtTokenUtils.getCurrentUser());
                    JwtTokenUtils.setSecurityContextAuthentication(token, userInfo);
                }
            } catch (Exception e) {
                logger.warn("token check failed:" + e.getMessage());
                response.setStatus(200);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                PrintWriter printWriter = response.getWriter();
                String body = FridayResult.fail(ResultStatus.INTERNAL_SERVER_ERROR.getCode(), e.getMessage()).toString();
                printWriter.write(body);
                printWriter.flush();
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
