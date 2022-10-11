package com.knifez.fridaybootadmin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
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
public class AppMenu implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("父级节点")
    private Integer parentId;

    @ApiModelProperty("菜单名称")
    private String name;

    @ApiModelProperty("菜单类型")
    private String type;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("路由地址")
    private String routePath;

    @ApiModelProperty("组件路径")
    private String comPath;

    @ApiModelProperty("权限标识")
    private String permission;

    @ApiModelProperty("是否显示")
    private Boolean isVisible;

    @ApiModelProperty("是否缓存")
    private Boolean isCached;

    @ApiModelProperty("是否启用")
    private Boolean isEnabled;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("修改人")
    private String updateBy;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;


}
