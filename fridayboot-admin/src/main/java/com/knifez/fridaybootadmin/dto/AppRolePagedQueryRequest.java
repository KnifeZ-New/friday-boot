package com.knifez.fridaybootadmin.dto;

import com.knifez.fridaybootcore.dto.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

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
}
