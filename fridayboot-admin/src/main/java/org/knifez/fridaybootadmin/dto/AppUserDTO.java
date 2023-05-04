package org.knifez.fridaybootadmin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.knifez.fridaybootadmin.entity.AppRole;

import java.util.ArrayList;
import java.util.List;

/**
 * @author KnifeZ
 */
@Getter
@Setter
@Schema(title = "AppUserDTO")
public class AppUserDTO {
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

    @Schema(title = "电话")
    private String phone;

    @Schema(title = "头像")
    private String avatar;

    @Schema(title = "是否启锁定")
    private Boolean locked;

    @Schema(title = "所属部门id")
    private Long organizationId;

    @Schema(title = "所属部门")
    private String organizationName;

    @Schema(title = "角色")
    private List<AppRole> roles = new ArrayList<>();
}
