package org.knifez.fridaybootadmin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * @author KnifeZ
 */
@Getter
@Setter
public class AppMenuQueryRequest {

    @Schema(defaultValue = "名称")
    private String name;

    @Schema(defaultValue = "状态")
    private Boolean enabled;

    @Schema(defaultValue = "是否返回按钮")
    private Boolean includeButton;
}
