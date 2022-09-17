package com.knifez.fridaybootadmin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;

import com.knifez.fridaybootcore.entity.BaseAuditEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 组织机构
 * </p>
 *
 * @author KnifeZ
 * @since 2022-09-01
 */
@Getter
@Setter
@TableName("app_organization_unit")
@ApiModel(value = "AppOrganizationUnit对象", description = "组织机构")
public class AppOrganizationUnit extends BaseAuditEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("机构编码")
    private String unitCode;


    @ApiModelProperty("父级节点")
    private Long parentId;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("简介")
    private String description;


}
