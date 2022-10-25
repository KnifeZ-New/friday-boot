package com.knifez.fridaybootadmin.dto;

import com.knifez.fridaybootcore.dto.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author zhang
 */
@Getter
@Setter
public class AppUserPagedQueryRequest extends PageRequest {

    @NotNull(message = "name 不能为空")
    @ApiModelProperty("用户姓名")
    private String username;

    @ApiModelProperty("部门")
    private Long organizationId;

    @ApiModelProperty("是否锁定")
    private Boolean locked;
}
