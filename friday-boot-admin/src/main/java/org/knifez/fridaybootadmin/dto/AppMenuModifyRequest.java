package org.knifez.fridaybootadmin.dto;


import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * @author KnifeZ
 */
@Getter
@Setter
public class AppMenuModifyRequest {

    @Schema(defaultValue = "主键id")
    private Integer id;

    @Schema(defaultValue = "父级节点")
    private Integer parentId;

    @Schema(defaultValue = "菜单名称")
    private String name;

    @Schema(defaultValue = "菜单标识")
    private String route;

    @Schema(defaultValue = "菜单类型")
    private String type;

    @Schema(defaultValue = "排序")
    private Integer sort;

    @Schema(defaultValue = "图标")
    private String icon;

    @Schema(defaultValue = "过场动画")
    private String transition;

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
