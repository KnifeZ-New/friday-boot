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
 * 字典
 * </p>
 *
 * @author KnifeZ
 * @since 2022-10-09
 */
@Getter
@Setter
@TableName("app_dictionary")
@ApiModel(value = "AppDictionary对象", description = "字典")
public class AppDictionary extends BaseAuditEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("字典名称")
    private String name;

    @ApiModelProperty("字典编码")
    private String code;

    @ApiModelProperty("备注")
    private String description;

    @TableField(value = "is_enabled")
    @ApiModelProperty("是否启用")
    private Boolean enabled;

}
