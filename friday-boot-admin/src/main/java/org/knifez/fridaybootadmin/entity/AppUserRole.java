package org.knifez.fridaybootadmin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.knifez.fridaybootcore.entity.BaseAuditEntity;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
@author KnifeZ
 * @since 2022-07-07
 */
@Getter
@Setter
@TableName("app_user_role")
@Schema(title = "AppUserRole对象", description = "用户角色关联表")
public class AppUserRole extends BaseAuditEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(title = "用户id")
    private Long userId;

    @Schema(title = "角色id")
    private Long roleId;


}
