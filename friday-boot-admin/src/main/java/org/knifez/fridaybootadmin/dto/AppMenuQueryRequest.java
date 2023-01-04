package org.knifez.fridaybootadmin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Knife
 */
@Getter
@Setter
public class AppMenuQueryRequest {

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("状态")
    private Boolean enabled;

    @ApiModelProperty("是否返回按钮")
    private Boolean includeButton;
}
