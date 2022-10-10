package com.knifez.fridaybootadmin.dto;

import com.knifez.fridaybootcore.dto.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppDictionaryQueryRequest extends PageRequest {

    @ApiModelProperty("字典名称")
    private String name;

    @ApiModelProperty("字典编码")
    private String code;
}
