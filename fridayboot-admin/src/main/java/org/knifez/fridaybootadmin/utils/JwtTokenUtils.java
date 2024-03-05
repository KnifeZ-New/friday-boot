package org.knifez.fridaybootadmin.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.xml.bind.DatatypeConverter;
import org.knifez.fridaybootadmin.common.constants.SecurityConst;
import org.knifez.fridaybootadmin.dto.Token;
import org.knifez.fridaybootcore.common.constants.AppConstants;
import org.knifez.fridaybootcore.utils.JwtUtils;
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
public class JwtTokenUtils {

    /**
     * 生成足够的安全随机密钥，以适合符合规范的签名
     */
    private static final byte[] API_KEY_SECRET_BYTES = DatatypeConverter.parseBase64Binary(AppConstants.JWT_SECRET_KEY);
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(API_KEY_SECRET_BYTES);

    private JwtTokenUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static Token createToken(String username, String id, List<String> roles, List<String> grantedAuthorities, boolean isRememberMe) {
        long expiration = isRememberMe ? SecurityConst.EXPIRATION_REMEMBER : SecurityConst.EXPIRATION;
        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + expiration * 1000);
        String tokenPrefix = Jwts.builder()
                .setHeaderParam("type", AppConstants.JWT_TOKEN_TYPE)
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .claim(SecurityConst.ROLE_CLAIMS, String.join(",", roles))
                .claim(SecurityConst.AUTHORITY_CLAIMS, grantedAuthorities)
                .setId(id)
                .setSubject(username)
                .setIssuer("fridayboot")
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .compact();

        String token = AppConstants.JWT_TOKEN_PREFIX + tokenPrefix;
        return new Token(token, expiration, "", 0L);
    }


    public static UsernamePasswordAuthenticationToken getAuthentication(String token) {
        Claims claims = JwtUtils.getClaims();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        String userName = null;
        if (claims != null) {
            var tempList = getAuthorities(claims);
            tempList.forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission)));
            userName = claims.getSubject();
        }
        return new UsernamePasswordAuthenticationToken(userName, token, authorities);
    }

    private static List<String> getAuthorities(Claims claims) {
        var obj = claims.get(SecurityConst.AUTHORITY_CLAIMS);
        if (obj instanceof List<?>) {
            return ((List<?>) obj).stream().map(Object::toString).toList();
        }
        return new ArrayList<>();
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
        Claims claims = JwtUtils.getClaims();
        String role = null;
        if (claims != null) {
            role = (String) claims.get(SecurityConst.ROLE_CLAIMS);
        }
        return role != null && Arrays.asList(role.split(",")).contains(AppConstants.ROLE_SUPER_ADMIN);
    }
}

