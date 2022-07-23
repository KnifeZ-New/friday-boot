package com.knifez.fridaybootadmin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhang
 */
@Getter
@Setter
public class Token {
    /**
     * 访问令牌
     */
    @ApiModelProperty("访问令牌")
    private String accessToken;
    /**
     * 令牌过期时间
     */
    @ApiModelProperty("令牌过期时间")
    private Long expires;
    /**
     * 刷新令牌
     */
    @ApiModelProperty("刷新令牌")
    private String refreshToken;
    /**
     * 刷新令牌时效
     */
    @ApiModelProperty("刷新令牌时效")
    private Long refreshTokenExpires;

    /**
     * 作用域
     */
    @ApiModelProperty("作用域")
    private String scope;

    public Token(String accessToken, Long expires, String refreshToken, Long refreshTokenExpires) {
        this.accessToken = accessToken;
        this.expires = expires;
        this.refreshToken = refreshToken;
        this.refreshTokenExpires = refreshTokenExpires;
    }
}
