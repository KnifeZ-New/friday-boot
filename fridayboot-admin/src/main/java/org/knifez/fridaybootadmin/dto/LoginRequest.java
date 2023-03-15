package org.knifez.fridaybootadmin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
@author KnifeZ
 * 用户登录请求DTO
 */
@Data
@AllArgsConstructor
@Schema(title = "LoginRequest")
public class LoginRequest {
    /**
     * 用户名
     */
    @Schema(title = "用户名")
    private String username;
    /**
     * 密码
     */
    @Schema(title = "密码")
    private String password;
    /**
     * 记住帐号
     */
    @Schema(title = "记住帐号")
    private Boolean rememberMe;
}
