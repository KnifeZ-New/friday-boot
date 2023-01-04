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
 * 字典
 * </p>
 *
@author KnifeZ
 * @since 2022-10-09
 */
@Getter
@Setter
@TableName("app_dictionary")
@Schema(defaultValue = "AppDictionary对象", description = "字典")
public class AppDictionary extends BaseAuditEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(defaultValue = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(defaultValue = "字典名称")
    private String name;

    @Schema(defaultValue = "字典编码")
    private String code;

    @Schema(defaultValue = "备注")
    private String description;

    @TableField(value = "is_enabled")
    @Schema(defaultValue = "是否启用")
    private Boolean enabled;

}
