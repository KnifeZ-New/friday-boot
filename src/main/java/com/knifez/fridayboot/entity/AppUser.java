package com.knifez.fridayboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import com.knifez.fridayboot.domain.entity.BaseAuditEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author KnifeZ
 * @since 2022-04-01
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TableName("app_user")
@ApiModel(value = "AppUser对象", description = "用户")
public class AppUser extends BaseAuditEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("账号")
    private String account;

    @ApiModelProperty("姓名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("邮箱")
    private String email;

    @TableField(value = "is_locked")
    @ApiModelProperty("是否启锁定")
    private Boolean locked;

    @TableField(exist = false)
    @ApiModelProperty("角色")
    private List<AppRole> roles;

    public List<SimpleGrantedAuthority> getRoles() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName())));
        return authorities;
    }

}
