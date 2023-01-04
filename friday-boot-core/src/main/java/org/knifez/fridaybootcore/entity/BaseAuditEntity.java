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
public class BaseAuditEntity {

    @Schema(defaultValue = "创建人")
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @Schema(defaultValue = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(defaultValue = "更新人")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    @Schema(defaultValue = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
