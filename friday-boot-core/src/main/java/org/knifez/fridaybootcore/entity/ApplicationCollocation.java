package org.knifez.fridaybootcore.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
@author KnifeZ
 */
@Getter
@Setter
public class ApplicationCollocation {
    @Schema(defaultValue = "当前用户id")
    private String userId;
    @Schema(defaultValue = "所属租户id")
    private String tenantId;
    @Schema(defaultValue = "角色")
    private List<String> roles;
    @Schema(defaultValue = "权限")
    private List<String> permissions;

    @Schema(defaultValue = "语言")
    private String language;

    @Schema(defaultValue = "暗黑模式")
    private Boolean darkMode;

    @Schema(defaultValue = "所有权限")
    private List<String> grantedPolicies;

}
