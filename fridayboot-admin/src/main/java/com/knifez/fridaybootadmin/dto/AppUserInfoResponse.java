package com.knifez.fridaybootadmin.dto;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("账号")
    private String account;

    @ApiModelProperty("姓名")
    private String username;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("角色")
    private List<String> userRoles = new ArrayList<>();

    @ApiModelProperty("权限")
    private List<String> permissions = new ArrayList<>();

    @ApiModelProperty("首页")
    public String homePath;
}
