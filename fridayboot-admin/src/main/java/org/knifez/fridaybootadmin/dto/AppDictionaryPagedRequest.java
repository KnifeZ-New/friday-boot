package org.knifez.fridaybootadmin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.knifez.fridaybootcore.dto.PagedRequest;

/**
 * @author KnifeZ
 */
@Getter
@Setter
@Schema(title = "AppDictionaryQueryRequest")
public class AppDictionaryPagedRequest extends PagedRequest {

    @Schema(title = "字典名称")
    private String name;

    @Schema(title = "是否启用")
    private Boolean enabled;
}
