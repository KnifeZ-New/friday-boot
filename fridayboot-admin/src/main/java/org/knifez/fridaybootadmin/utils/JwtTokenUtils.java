package org.knifez.fridaybootadmin.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.xml.bind.DatatypeConverter;
import org.knifez.fridaybootadmin.constants.SecurityConst;
import org.knifez.fridaybootadmin.dto.Token;
import org.knifez.fridaybootcore.constants.AppConstants;
import org.knifez.fridaybootcore.utils.JwtUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.crypto.SecretKey;
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

    public static Token createToken(String username, String id, List<String> roles, boolean isRememberMe) {
        long expiration = isRememberMe ? SecurityConst.EXPIRATION_REMEMBER : SecurityConst.EXPIRATION;
        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + expiration * 1000);
        String tokenPrefix = Jwts.builder()
                .setHeaderParam("type", AppConstants.JWT_TOKEN_TYPE)
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .claim(SecurityConst.ROLE_CLAIMS, String.join(",", roles))
                .setId(id)
                .setIssuer("fridayboot")
                .setIssuedAt(createdDate)
                .setSubject(username)
                .setExpiration(expirationDate)
                .compact();

        String token = AppConstants.JWT_TOKEN_PREFIX + tokenPrefix;
        return new Token(token, expiration, "", 0L);
    }


    public static UsernamePasswordAuthenticationToken getAuthentication(String token) {
        Claims claims = JwtUtils.getClaims();
        List<SimpleGrantedAuthority> authorities = null;
        String userName = null;
        if (claims != null) {
            authorities = getAuthorities(claims);
            userName = claims.getSubject();
        }
        return new UsernamePasswordAuthenticationToken(userName, token, authorities);
    }

    private static List<SimpleGrantedAuthority> getAuthorities(Claims claims) {
        String role = (String) claims.get(SecurityConst.ROLE_CLAIMS);
        return Arrays.stream(role.split(","))
                .map(SimpleGrantedAuthority::new)
                .toList();
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
}

