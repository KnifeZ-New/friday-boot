package org.knifez.fridaybootadmin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.knifez.fridaybootcore.dto.PageRequest;

/**
@author KnifeZ
 */
@Getter
@Setter
public class AppRolePagedQueryRequest extends PageRequest {

    @Schema(defaultValue = "角色编码")
    private String name;

    @Schema(defaultValue = "角色名称")
    private String displayName;

    @Schema(defaultValue = "状态")
    private Boolean enabled;
}
