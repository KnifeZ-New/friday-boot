package com.knifez.fridaybootadmin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.knifez.fridaybootcore.entity.BaseAuditEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 *
 * </p>
 *
 * @author KnifeZ
 * @since 2022-07-06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("app_role")
@ApiModel(value = "AppRole对象", description = "角色")
public class AppRole extends BaseAuditEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("角色")
    private String name;

    @ApiModelProperty("名称")
    private String displayName;

    @ApiModelProperty("角色介绍")
    private String description;

    @TableField(exist = false)
    @ApiModelProperty("角色权限")
    private List<Integer> permissions;

}
