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
    @Schema(title = "当前用户id")
    private String userId;
    @Schema(title = "所属租户id")
    private String tenantId;
    @Schema(title = "角色")
    private List<String> roles;
    @Schema(title = "权限")
    private List<String> permissions;

    @Schema(title = "语言")
    private String language;

    @Schema(title = "暗黑模式")
    private Boolean darkMode;

    @Schema(title = "所有权限")
    private List<String> grantedPolicies;

}
