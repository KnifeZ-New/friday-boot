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
@Schema(title = "AppUserInfoDTO")
public class AppUserInfoDTO {
    @Schema(title = "首页")
    public String homePath;
    @Schema(title = "主键id")
    private Long id;
    @Schema(title = "账号")
    private String account;
    @Schema(title = "姓名")
    private String username;
    @Schema(title = "邮箱")
    private String email;
    @Schema(title = "头像")
    private String avatar;
    @Schema(title = "角色")
    private List<String> userRoles;
    @Schema(title = "权限")
    private List<String> permissions;
}
