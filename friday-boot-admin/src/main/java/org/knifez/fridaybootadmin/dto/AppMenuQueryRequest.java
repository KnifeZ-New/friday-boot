package org.knifez.fridaybootadmin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * @author KnifeZ
 */
@Getter
@Setter
@Schema(title = "AppMenuQueryRequest")
public class AppMenuQueryRequest {

    @Schema(title = "名称")
    private String name;

    @Schema(title = "状态")
    private Boolean enabled;

    @Schema(title = "是否返回按钮")
    private Boolean includeButton;
}
