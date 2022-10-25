package com.knifez.fridaybootadmin.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Knife
 */
@Getter
@Setter
@ApiModel(value = "AppMenuButton对象", description = "菜单按钮")
public class AppMenuButton extends AppMenu {

    @ApiModelProperty("标签颜色")
    private String tagColor;
}
