package com.knifez.fridayboot.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author zhang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseAuditEntity {

    @TableField(fill = FieldFill.INSERT)
    public String createBy;

    @TableField(fill = FieldFill.INSERT)
    public LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    public String updateBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    public LocalDateTime updateTime;

}
