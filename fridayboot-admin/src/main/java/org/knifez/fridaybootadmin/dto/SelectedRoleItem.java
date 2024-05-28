package org.knifez.fridaybootadmin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(title = "SelectedRoleItem")
public class SelectedRoleItem {
    @Schema(title = "主键id")
    private Integer id;

    @Schema(title = "角色")
    private String name;

    @Schema(title = "名称")
    private String displayName;
}
