package org.knifez.fridaybootadmin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.knifez.fridaybootcore.dto.PageRequest;

/**
 * @author zhang
 */
@Getter
@Setter
public class AppRolePagedQueryRequest extends PageRequest {

    @ApiModelProperty("角色编码")
    private String name;

    @ApiModelProperty("角色名称")
    private String displayName;

    @ApiModelProperty("状态")
    private Boolean enabled;
}
