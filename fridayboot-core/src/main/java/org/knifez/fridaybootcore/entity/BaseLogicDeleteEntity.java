package org.knifez.fridaybootcore.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * @author KnifeZ
 */
@Getter
@Setter
public class BaseLogicDeleteEntity extends BaseAuditEntity {

    @TableField(value = "is_deleted")
    @TableLogic
    @Schema(title = "是否删除")
    private boolean deleted;

}
