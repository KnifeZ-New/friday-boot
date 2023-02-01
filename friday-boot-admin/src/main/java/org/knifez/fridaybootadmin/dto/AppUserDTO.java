package org.knifez.fridaybootadmin.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.knifez.fridaybootadmin.entity.AppRole;
import org.knifez.fridaybootcore.entity.BaseAuditEntity;

import java.util.List;

/**
 * @author KnifeZ
 */
@Getter
@Setter
@ToString
public class AppUserDTO extends BaseAuditEntity {
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

    @TableField(value = "is_locked")
    @Schema(defaultValue = "是否锁定")
    private Boolean locked;

    @Schema(defaultValue = "所属部门id")
    private Long organizationId;
    @Schema(defaultValue = "所属部门")
    private String organizationName;

    @Schema(defaultValue = "角色")
    private List<AppRole> roles;
}
