package org.knifez.fridaybootadmin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * @author KnifeZ
 */
@Getter
@Setter
@Schema(title = "AppMenuButtonQueryRequest")
public class AppMenuButtonQueryRequest {

    @Schema(title = "菜单ID")
    private int menuId;

    @Schema(title = "按钮名称")
    private String name;

    @Schema(title = "是否启用")
    private Boolean enabled;
}
