package org.knifez.fridaybootcore.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author KnifeZ
 */
@Getter
@Setter
public class BaseFullAuditEntity extends BaseAuditEntity {

    @TableField(value = "is_deleted")
    @Schema(title = "是否删除")
    private boolean deleted;

    @Schema(title = "删除人")
    @TableField(fill = FieldFill.UPDATE)
    private String deleteBy;

    @Schema(title = "删除时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime deleteTime;

}
