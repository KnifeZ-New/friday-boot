package com.knifez.fridaybootadmin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.knifez.fridaybootcore.entity.BaseAuditEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

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
@ApiModel(value = "AppMenu对象", description = "菜单")
public class AppMenu extends BaseAuditEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("父级节点")
    private Integer parentId;

    @ApiModelProperty("菜单名称")
    private String name;

    @ApiModelProperty("菜单标识")
    private String route;

    @ApiModelProperty("菜单类型")
    private String type;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("过场动画")
    private String transition;

    @ApiModelProperty("徽章")
    private String badge;

    @ApiModelProperty("路由地址")
    private String routePath;

    @ApiModelProperty("组件路径")
    private String component;

    @ApiModelProperty("权限标识")
    private String permission;

    @ApiModelProperty("是否缓存")
    private Boolean keepAlive;

    @TableField(value = "is_fixed")
    @ApiModelProperty("是否固定")
    private Boolean fixed;

    @TableField(value = "is_hot")
    @ApiModelProperty("是否热点")
    private Boolean hot;

    @TableField(value = "is_visible")
    @ApiModelProperty("是否显示")
    private Boolean visible;

    @TableField(value = "is_enabled")
    @ApiModelProperty("是否启用")
    private Boolean enabled;

    @ApiModelProperty("备注")
    private String remark;

}
