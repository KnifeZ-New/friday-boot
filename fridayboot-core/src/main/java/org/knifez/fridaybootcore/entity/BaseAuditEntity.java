package org.knifez.fridaybootcore.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
@author KnifeZ
 */
@Getter
@Setter
@Schema(title = "BaseAuditEntity")
public class BaseAuditEntity {

    @Schema(title = "创建人")
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @Schema(title = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(title = "更新人")
    @TableField(fill = FieldFill.UPDATE)
    private String updateBy;

    @Schema(title = "更新时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

}
