package org.knifez.fridaybootadmin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Knife
 */
@Getter
@Setter
public class AppDictionaryConfigQueryRequest {
    @Schema(defaultValue = "字典名称")
    private String name;

    @Schema(defaultValue = "是否启用")
    private Boolean enabled;
}
