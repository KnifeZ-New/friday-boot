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
@Schema(title = "AppMenuModifyRequest")
public class AppMenuModifyRequest {

    @Schema(title = "主键id")
    private Integer id;

    @Schema(title = "父级节点")
    private Integer parentId;

    @Schema(title = "菜单名称")
    private String name;

    @Schema(title = "菜单标识")
    private String route;

    @Schema(title = "菜单类型")
    private Integer type;

    @Schema(title = "排序")
    private Integer sort;

    @Schema(title = "图标")
    private String icon;

    @Schema(title = "过场动画")
    private String transition;

    @Schema(title = "徽章")
    private String badge;

    @Schema(title = "路由地址")
    private String routePath;

    @Schema(title = "组件路径")
    private String component;

    @Schema(title = "权限标识")
    private String permission;

    @Schema(title = "是否缓存")
    private Boolean keepAlive;

    @TableField(value = "is_fixed")
    @Schema(title = "是否固定")
    private Boolean fixed;

    @TableField(value = "is_hot")
    @Schema(title = "是否热点")
    private Boolean hot;

    @TableField(value = "is_visible")
    @Schema(title = "是否显示")
    private Boolean visible;

    @TableField(value = "is_enabled")
    @Schema(title = "是否启用")
    private Boolean enabled;

    @Schema(title = "备注")
    private String remark;
}
