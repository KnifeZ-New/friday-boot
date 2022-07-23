package com.knifez.fridaybootcore.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author zhang
 */
@Getter
@Setter
public class ApplicationCollocation {
    @ApiModelProperty("当前用户id")
    private String userId;
    @ApiModelProperty("所属租户id")
    private String tenantId;
    @ApiModelProperty("角色")
    private List<String> roles;
    @ApiModelProperty("权限")
    private List<String> permissions;
    @ApiModelProperty("语言")
    private String language;

}
