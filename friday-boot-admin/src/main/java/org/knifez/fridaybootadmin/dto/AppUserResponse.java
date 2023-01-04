package org.knifez.fridaybootadmin.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.knifez.fridaybootadmin.entity.AppRole;
import org.knifez.fridaybootcore.entity.BaseAuditEntity;

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
    @ApiModelProperty("所属部门")
    private String organizationName;

    @ApiModelProperty("角色")
    private List<AppRole> roles = new ArrayList<>();
}
