package org.knifez.fridaybootcore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * @author KnifeZ
 */
@Getter
@Setter
@Schema(title = "AppDictionaryQueryRequest")
public class AppDictionaryQueryRequest extends PagedRequest {

    @Schema(title = "字典名称")
    private String name;

    @Schema(title = "是否启用")
    private Boolean enabled;
}
