package org.knifez.fridaybootadmin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.ApiStatus;
import org.knifez.fridaybootadmin.common.MenuTypeEnum;
import org.knifez.fridaybootcore.entity.BaseFullAuditEntity;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 菜单
 * </p>
 *
 * @author KnifeZ
 * @since 2022-10-11
 */
@Getter
@Setter
@TableName("app_menu")
@Schema(defaultValue = "AppMenu对象", description = "菜单")
public class AppMenu extends BaseFullAuditEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(defaultValue = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(defaultValue = "父级节点")
    private Integer parentId;

    @Schema(defaultValue = "菜单名称")
    private String name;

    @Schema(defaultValue = "菜单标识")
    private String route;

    @Schema(defaultValue = "菜单类型")
    private MenuTypeEnum type;

    @Schema(defaultValue = "排序")
    private Integer sort;

    @Schema(defaultValue = "图标")
    private String icon;

    @ApiStatus.Obsolete
    @Schema(defaultValue = "过场动画")
    private String transition;

    @ApiStatus.Obsolete
    @Schema(defaultValue = "徽章")
    private String badge;

    @Schema(defaultValue = "路由地址")
    private String routePath;

    @Schema(defaultValue = "组件路径")
    private String component;

    @Schema(defaultValue = "权限标识")
    private String permission;

    @Schema(defaultValue = "是否缓存")
    private Boolean keepAlive;

    @TableField(value = "is_fixed")
    @Schema(defaultValue = "是否固定")
    private Boolean fixed;

    @TableField(value = "is_hot")
    @Schema(defaultValue = "是否热点")
    private Boolean hot;

    @TableField(value = "is_visible")
    @Schema(defaultValue = "是否显示")
    private Boolean visible;

    @TableField(value = "is_enabled")
    @Schema(defaultValue = "是否启用")
    private Boolean enabled;

    @Schema(defaultValue = "备注")
    private String remark;

}
