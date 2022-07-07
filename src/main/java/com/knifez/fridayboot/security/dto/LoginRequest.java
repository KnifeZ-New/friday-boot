package com.knifez.fridayboot.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author zhang
 * 用户登录请求DTO
 */
@Data
@AllArgsConstructor
public class LoginRequest {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 记住帐号
     */
    private Boolean rememberMe;
}
