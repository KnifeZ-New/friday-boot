package org.knifez.fridaybootadmin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Knife
 */
@Getter
@Setter
public class AppMenuButtonQueryRequest {

    @ApiModelProperty("菜单ID")
    private int menuId;

    @ApiModelProperty("按钮名称")
    private String name;

    @ApiModelProperty("是否启用")
    private Boolean enabled;
}
