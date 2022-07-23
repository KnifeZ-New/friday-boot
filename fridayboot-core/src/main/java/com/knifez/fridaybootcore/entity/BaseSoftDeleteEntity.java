package com.knifez.fridaybootcore.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhang
 */
@Getter
@Setter
public class BaseSoftDeleteEntity {
    @TableField(value = "is_deleted")
    private int deleted;
}
