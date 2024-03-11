package org.knifez.fridaybootadmin.utils;

import cn.hutool.jwt.JWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.xml.bind.DatatypeConverter;
import lombok.extern.slf4j.Slf4j;
import org.knifez.fridaybootadmin.common.constants.SecurityConst;
import org.knifez.fridaybootadmin.dto.AppUserInfoDTO;
import org.knifez.fridaybootadmin.dto.JwtUserDetail;
import org.knifez.fridaybootadmin.dto.Token;
import org.knifez.fridaybootcore.common.constants.AppConstants;
import org.knifez.fridaybootcore.utils.ServletRequestUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
        var expiration = isRememberMe ? SecurityConst.EXPIRATION_REMEMBER : SecurityConst.EXPIRATION;
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
    public static void setSecurityContextAuthentication(String token, String jwtUser) {
        if (jwtUser != null) {
            Claims claims = getClaims();
            if (claims != null) {
                String userName = claims.getSubject();
                JwtUserDetail userDetail = new JwtUserDetail(jwtUser);
                token = fixToken(token);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName, token, userDetail.getAuthorities());
                authenticationToken.setDetails(userDetail);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
    }


    public static boolean checkLogin(String currentPassword, String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.matches(currentPassword, password);
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

    public static String getCurrentUser() {
        Claims claims = getClaims();
        if (claims == null) {
            return "";
        }
        return claims.getSubject();
    }

    public static JwtUserDetail getJwtUserDetail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getDetails() != null) {
            try {
                return (JwtUserDetail) authentication.getDetails();
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        return new JwtUserDetail(new AppUserInfoDTO());
    }

    /**
     * 获取数据权限
     *
     * @return {@link List}<{@link String}>
     */
    public static List<Integer> getDataPermission(List<Integer> selected) {
        List<Integer> result = new ArrayList<>();
        var user = getJwtUserDetail();
        var dataPermissions = user.getDataPermissions();
        if (selected != null && !selected.isEmpty()) {
            result.addAll(selected);
            // 移除不在数据权限列表的数据,超管不移除
            if (!JwtTokenUtils.isSuperAdmin()) {
                result.removeIf(item -> dataPermissions.stream().noneMatch(x -> x.equals(item)));
            }
        } else {
            result.addAll(dataPermissions);
        }
        if (JwtTokenUtils.isSuperAdmin()) {
            return result;
        }
        // 非超管且无数据权限
        if (result.isEmpty()) {
            result.add(0);
        }
        return result;
    }

    public static Boolean isExpired(String token) {
        try {
            var exp = JWT.of(fixToken(token)).getPayload(JWT.EXPIRES_AT);
            if (System.currentTimeMillis() / 1000 > Long.parseLong(exp.toString())) {
                return true;
            }
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return true;
        }
        return false;
    }

    private static String fixToken(String token) {
        return token.replace(AppConstants.JWT_TOKEN_PREFIX, "");
    }
}

