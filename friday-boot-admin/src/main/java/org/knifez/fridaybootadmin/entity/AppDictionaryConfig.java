package org.knifez.fridaybootadmin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * 字典配置
 * </p>
 *
@author KnifeZ
 * @since 2022-10-09
 */
@Getter
@Setter
@TableName("app_dictionary_config")
@Schema(defaultValue = "AppDictionaryConfig对象", description = "字典配置")
public class AppDictionaryConfig extends BaseAuditEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(defaultValue = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(defaultValue = "字典编码")
    private String dictCode;

    @Schema(defaultValue = "父级节点")
    private Long parentId;

    @Schema(defaultValue = "名字")
    private String name;

    @Schema(defaultValue = "数据类型")
    private String valueType;

    @Schema(defaultValue = "数据值")
    private String value;

    @Schema(defaultValue = "标签等级")
    private String labelLevel;

    @Schema(defaultValue = "图标")
    private String icon;

    @Schema(defaultValue = "排序")
    private Integer sort;

    @Schema(defaultValue = "备注")
    private String description;

    @TableField(value = "is_enabled")
    @Schema(defaultValue = "是否启用")
    private Boolean enabled;


}
