package com.knifez.fridayboot.domain.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.knifez.fridayboot.domain.entity.BaseAuditEntity;
import com.knifez.fridayboot.entity.AppRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

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

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("邮箱")
    private String email;

    @TableField(value = "is_locked")
    @ApiModelProperty("是否启锁定")
    private Boolean locked;

    @ApiModelProperty("角色")
    private List<AppRole> roles;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        AppUserResponse that = (AppUserResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(account, that.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, account);
    }
}
