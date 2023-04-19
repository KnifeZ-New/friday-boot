package org.knifez.fridaybootadmin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppUserResetPasswordRequest {

    @Schema(title = "主键id")
    private Long id;

    @Schema(title = "原密码")
    private String originPassword;

    @Schema(title = "新密码")
    private String newPassword;

    @Schema(title = "跳过原密码验证")
    private Boolean skipCheckPassword;
}
