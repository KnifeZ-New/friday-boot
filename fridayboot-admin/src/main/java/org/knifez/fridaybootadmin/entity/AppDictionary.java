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
@Schema(title = "AppDictionary对象", description = "字典")
public class AppDictionary extends BaseAuditEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(title = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(title = "字典名称")
    private String name;

    @Schema(title = "字典编码")
    private String code;

    @Schema(title = "备注")
    private String description;

    @TableField(value = "is_enabled")
    @Schema(title = "是否启用", description = "是否启用")
    private Boolean enabled;

}
