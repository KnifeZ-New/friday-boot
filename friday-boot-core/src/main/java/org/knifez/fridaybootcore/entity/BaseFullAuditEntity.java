package org.knifez.fridaybootcore.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author zhang
 */
@Getter
@Setter
public class BaseFullAuditEntity extends BaseAuditEntity {

    @TableField(value = "is_deleted")
    @ApiModelProperty("是否删除")
    private int deleted;

    @ApiModelProperty("删除人")
    @TableField(fill = FieldFill.UPDATE)
    private String deleteBy;

    @ApiModelProperty("删除时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime deleteTime;

}
