package org.knifez.fridaybootadmin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.knifez.fridaybootcore.dto.PageRequest;

/**
 * @author Knife
 */
@Getter
@Setter
public class AppDictionaryQueryRequest extends PageRequest {

    @ApiModelProperty("字典名称")
    private String name;

    @ApiModelProperty("是否启用")
    private Boolean enabled;
}
