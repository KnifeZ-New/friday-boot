package com.knifez.fridaybootadmin.dto;

import com.knifez.fridaybootcore.dto.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppOrganizationUnitQueryRequest extends PageRequest {

    @ApiModelProperty("机构编码")
    private String unitCode;

    @ApiModelProperty("名称")
    private String name;
}
