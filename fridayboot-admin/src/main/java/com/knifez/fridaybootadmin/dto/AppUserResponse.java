package com.knifez.fridaybootadmin.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.knifez.fridaybootadmin.entity.AppRole;
import com.knifez.fridaybootcore.entity.BaseAuditEntity;
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
public class AppUserResponse extends BaseAuditEntity {
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

    @TableField(value = "is_locked")
    @ApiModelProperty("是否锁定")
    private Boolean locked;

    @ApiModelProperty("所属部门id")
    private Long organizationId;

    @ApiModelProperty("角色")
    private List<AppRole> roles = new ArrayList<>();
}
