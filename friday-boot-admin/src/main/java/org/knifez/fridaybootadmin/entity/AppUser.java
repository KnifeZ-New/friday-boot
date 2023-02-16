package org.knifez.fridaybootadmin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.knifez.fridaybootcore.entity.BaseAuditEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户表
 * </p>
 *
@author KnifeZ
 * @since 2022-04-01
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TableName("app_user")
@Schema(title = "AppUser对象", description = "用户")
public class AppUser extends BaseAuditEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(title = "主键id")
    private Long id;

    @Schema(title = "账号")
    private String account;

    @Schema(title = "姓名")
    private String username;

    @Schema(title = "密码")
    private String password;

    @Schema(title = "邮箱")
    private String email;

    @TableField(value = "is_locked")
    @Schema(title = "是否启锁定")
    private Boolean locked;

    @Schema(title = "所属部门id")
    private Long organizationId;

    /**
     * 部门名称
     */
    @Schema(title = "所属部门")
    @TableField(exist = false)
    private String organizationName;

    @TableField(exist = false)
    @Schema(title = "角色")
    private List<Long> roles = new ArrayList<>();

    @TableField(exist = false)
    @JsonIgnore
    @Schema(title = "角色")
    private List<String> userRoles = new ArrayList<>();

    @TableField(exist = false)
    @JsonIgnore
    @Schema(title = "权限")
    private List<String> permissions = new ArrayList<>();

    public List<SimpleGrantedAuthority> getGrantRoles() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        userRoles.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role)));
        if (authorities.isEmpty()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_GUEST"));
        }
        return authorities;
    }
}
