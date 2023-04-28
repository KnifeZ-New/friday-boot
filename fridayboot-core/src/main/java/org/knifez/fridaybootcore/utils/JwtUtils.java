package org.knifez.fridaybootcore.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.xml.bind.DatatypeConverter;
import org.knifez.fridaybootcore.constants.AppConstants;

import javax.crypto.SecretKey;

public class JwtUtils {
    private static final byte[] API_KEY_SECRET_BYTES = DatatypeConverter.parseBase64Binary(AppConstants.JWT_SECRET_KEY);
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(API_KEY_SECRET_BYTES);

    public static String getJWTToken() {
        var token = ServletRequestUtils.getRequest().getHeader(AppConstants.JWT_TOKEN_HEADER);
        token = token.replace(AppConstants.JWT_TOKEN_PREFIX, "");
        return token;
    }

    public static Claims getClaims() {
        var token = getJWTToken();
        return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static String getCurrentUser() {
        Claims claims = getClaims();
        return claims.getSubject();
    }

    public static Boolean isExpired(String token) {
        try {
            token = token.replace(AppConstants.JWT_TOKEN_PREFIX, "");
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build()
                    .parse(token);
        } catch (Exception exception) {
            exception.printStackTrace();
            return true;
        }
        return false;
    }
}
