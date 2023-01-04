package com.knifez.fridaybootadmin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Knife
 */
@Getter
@Setter
public class AppOrganizationUnitQueryRequest {

    @ApiModelProperty("机构编码")
    private String unitCode;

    @ApiModelProperty("名称")
    private String name;
}
