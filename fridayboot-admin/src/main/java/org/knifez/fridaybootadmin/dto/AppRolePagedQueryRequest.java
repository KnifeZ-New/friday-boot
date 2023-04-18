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
@Schema(title = "AppRolePagedQueryRequest")
public class AppRolePagedQueryRequest extends PagedRequest {

    @Schema(title = "角色编码")
    private String name;

    @Schema(title = "角色名称")
    private String displayName;

    @Schema(title = "状态")
    private Boolean enabled;
}
