package com.knifez.fridaybootadmin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * 字典配置
 * </p>
 *
 * @author KnifeZ
 * @since 2022-10-09
 */
@Getter
@Setter
@TableName("app_dictionary_config")
@ApiModel(value = "AppDictionaryConfig对象", description = "字典配置")
public class AppDictionaryConfig extends BaseAuditEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("字典编码")
    private String dictCode;

    @ApiModelProperty("父级节点")
    private Long parentId;

    @ApiModelProperty("名字")
    private String name;

    @ApiModelProperty("数据类型")
    private String valueType;

    @ApiModelProperty("数据值")
    private String value;

    @ApiModelProperty("标签等级")
    private String labelLevel;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("备注")
    private String description;

    @TableField(value = "is_enabled")
    @ApiModelProperty("是否启用")
    private Boolean enabled;


}
