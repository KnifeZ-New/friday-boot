package org.knifez.fridaybootadmin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Knife
 */
@Getter
@Setter
public class AppMenuButtonQueryRequest {

    @Schema(defaultValue = "菜单ID")
    private int menuId;

    @Schema(defaultValue = "按钮名称")
    private String name;

    @Schema(defaultValue = "是否启用")
    private Boolean enabled;
}
