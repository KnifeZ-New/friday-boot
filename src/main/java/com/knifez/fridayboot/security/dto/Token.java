package com.knifez.fridayboot.security.dto;

import lombok.Data;

/**
 * @author zhang
 */
@Data
public class Token {
    /**
     * 访问令牌
     */
    public String accessToken;
    /**
     * token过期时间
     */
    public Long expires;
    /**
     * 刷新token
     */
    public String refreshToken;
    /**
     * 刷新token时效
     */
    public Long refreshTokenExpires;

    /**
     * 作用域
     */
    public String scope;

    public Token(String accessToken, Long expires, String refreshToken, Long refreshTokenExpires) {
        this.accessToken = accessToken;
        this.expires = expires;
        this.refreshToken = refreshToken;
        this.refreshTokenExpires = refreshTokenExpires;
    }
}
