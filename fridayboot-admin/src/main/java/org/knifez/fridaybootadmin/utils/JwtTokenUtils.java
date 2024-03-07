package org.knifez.fridaybootadmin.utils;

import cn.hutool.jwt.JWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.xml.bind.DatatypeConverter;
import lombok.extern.slf4j.Slf4j;
import org.knifez.fridaybootadmin.common.constants.SecurityConst;
import org.knifez.fridaybootadmin.dto.Token;
import org.knifez.fridaybootcore.common.constants.AppConstants;
import org.knifez.fridaybootcore.utils.ServletRequestUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author KnifeZ
 */
@Slf4j
public class JwtTokenUtils {

    /**
     * 生成足够的安全随机密钥，以适合符合规范的签名
     */
    private static final byte[] API_KEY_SECRET_BYTES = DatatypeConverter.parseBase64Binary(AppConstants.JWT_SECRET_KEY);
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(API_KEY_SECRET_BYTES);

    private JwtTokenUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static Token createToken(String username, String id, List<String> roles, boolean isRememberMe) {
        long expiration = isRememberMe ? SecurityConst.EXPIRATION_REMEMBER : SecurityConst.EXPIRATION;
        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + expiration * 1000);
        String tokenPrefix = Jwts.builder()
                .setHeaderParam("type", AppConstants.JWT_TOKEN_TYPE)
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .claim(SecurityConst.ROLE_CLAIMS, String.join(",", roles))
                .setId(id)
                .setSubject(username)
                .setIssuer("fridayboot")
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .compact();

        String token = AppConstants.JWT_TOKEN_PREFIX + tokenPrefix;
        return new Token(token, expiration, "", 0L);
    }


    /**
     * 设置安全上下文身份验证
     *
     * @param token 令牌
     */
    public static void setSecurityContextAuthentication(String token, String authorities) {
        token = fixToken(token);
        Claims claims = getClaims();
        List<SimpleGrantedAuthority> authoritiesList = new ArrayList<>();
        String userName = null;
        if (claims != null) {
            var tempList = Arrays.stream(authorities.split(",")).toList();
            tempList.forEach(permission -> authoritiesList.add(new SimpleGrantedAuthority(permission)));
            userName = claims.getSubject();
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName, token, authoritiesList);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }


    public static boolean checkLogin(String currentPassword, String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.matches(currentPassword, password);
    }

    public static String getCurrentUserAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null) {
            return (String) authentication.getPrincipal();
        }
        return null;
    }

    public static boolean isSuperAdmin() {
        Claims claims = getClaims();
        String role = null;
        if (claims != null) {
            role = (String) claims.get(SecurityConst.ROLE_CLAIMS);
        }
        return role != null && Arrays.asList(role.split(",")).contains(AppConstants.ROLE_SUPER_ADMIN);
    }


    public static String getJWTToken() {
        var token = ServletRequestUtils.getRequest().getHeader(AppConstants.JWT_TOKEN_HEADER);
        if (token == null) return null;
        token = fixToken(token);
        return token;
    }

    public static Claims getClaims() {
        var token = getJWTToken();
        if (token == null) return null;
        return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build()
                .parseClaimsJws(token)
                .getBody();
    }

    private static String fixToken(String token) {
        return token.replace(AppConstants.JWT_TOKEN_PREFIX, "");
    }

    public static String getCurrentUser() {
        Claims claims = getClaims();
        if (claims == null) {
            return "";
        }
        return claims.getSubject();
    }

    public static Boolean isExpired(String token) {
        try {
            token = fixToken(token);
            var exp = JWT.of(token).getPayload(JWT.EXPIRES_AT);
            log.info(exp.toString());
            if (System.currentTimeMillis() / 1000 > Long.parseLong(exp.toString())) {
                return true;
            }
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return true;
        }
        return false;
    }
}

