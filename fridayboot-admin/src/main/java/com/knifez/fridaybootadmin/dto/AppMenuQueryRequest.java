package com.knifez.fridaybootadmin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppMenuQueryRequest {

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("状态")
    private boolean enabled;
}
