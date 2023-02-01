package org.knifez.fridaybootadmin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author KnifeZ
 */
@Getter
@Setter
@ToString
public class AppUserInfoDTO {
    @Schema(defaultValue = "首页")
    public String homePath;
    @Schema(defaultValue = "主键id")
    private Long id;
    @Schema(defaultValue = "账号")
    private String account;
    @Schema(defaultValue = "姓名")
    private String username;
    @Schema(defaultValue = "邮箱")
    private String email;
    @Schema(defaultValue = "头像")
    private String avatar;
    @Schema(defaultValue = "角色")
    private List<String> userRoles;
    @Schema(defaultValue = "权限")
    private List<String> permissions;
}
