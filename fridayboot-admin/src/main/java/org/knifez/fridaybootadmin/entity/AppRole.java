package org.knifez.fridaybootadmin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.knifez.fridaybootcore.entity.BaseAuditEntity;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author KnifeZ
 * @since 2022-07-06
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TableName("app_role")
@Schema(title = "AppRole对象", description = "角色")
public class AppRole extends BaseAuditEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(title = "主键id")
    private Long id;

    @Schema(title = "角色")
    private String name;

    @Schema(title = "名称")
    private String displayName;

    @Schema(title = "角色介绍")
    private String description;

    @TableField(value = "is_enabled")
    @Schema(title = "状态")
    private Boolean enabled;

    @TableField(exist = false)
    @Schema(title = "角色权限")
    private List<String> permissions;

}
