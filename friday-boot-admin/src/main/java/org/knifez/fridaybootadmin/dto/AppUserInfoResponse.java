package org.knifez.fridaybootadmin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhang
 */
@Getter
@Setter
public class AppUserInfoResponse {
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
    private List<String> userRoles = new ArrayList<>();
    @Schema(defaultValue = "权限")
    private List<String> permissions = new ArrayList<>();
}
