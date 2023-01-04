package org.knifez.fridaybootadmin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 授权记录
 * </p>
 *
@author KnifeZ
 * @since 2022-07-23
 */
@Getter
@Setter
@TableName("app_permission_grant")
@Schema(defaultValue = "AppPermissionGrant对象", description = "授权记录")
public class AppPermissionGrant implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(defaultValue = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(defaultValue = "权限")
    private String name;

    @Schema(defaultValue = "授权类型")
    private String provideName;

    @Schema(defaultValue = "授权对象")
    private String provideFor;


}
