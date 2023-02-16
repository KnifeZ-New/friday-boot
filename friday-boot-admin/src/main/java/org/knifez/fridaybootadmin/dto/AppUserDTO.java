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
@Schema(title = "AppUserDTO")
public class AppUserDTO extends BaseAuditEntity {
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

    @TableField(value = "is_locked")
    @Schema(title = "是否锁定")
    private Boolean locked;

    @Schema(title = "所属部门id")
    private Long organizationId;
    @Schema(title = "所属部门")
    private String organizationName;

    @Schema(title = "角色")
    private List<AppRole> roles;
}
