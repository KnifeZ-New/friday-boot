package org.knifez.fridaybootadmin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.knifez.fridaybootcore.dto.PageRequest;

/**
 * @author Knife
 */
@Getter
@Setter
public class AppDictionaryQueryRequest extends PageRequest {

    @Schema(defaultValue = "字典名称")
    private String name;

    @Schema(defaultValue = "是否启用")
    private Boolean enabled;
}
