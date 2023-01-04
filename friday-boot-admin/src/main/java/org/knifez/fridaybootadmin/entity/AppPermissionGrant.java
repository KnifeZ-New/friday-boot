package org.knifez.fridaybootadmin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 授权记录
 * </p>
 *
 * @author KnifeZ
 * @since 2022-07-23
 */
@Getter
@Setter
@TableName("app_permission_grant")
@ApiModel(value = "AppPermissionGrant对象", description = "授权记录")
public class AppPermissionGrant implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("权限")
    private String name;

    @ApiModelProperty("授权类型")
    private String provideName;

    @ApiModelProperty("授权对象")
    private String provideFor;


}
