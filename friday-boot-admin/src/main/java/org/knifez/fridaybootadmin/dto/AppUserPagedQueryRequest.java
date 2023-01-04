package org.knifez.fridaybootadmin.dto;

import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.knifez.fridaybootcore.dto.PageRequest;

import java.util.List;

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
    private List<Long> organizationIds;

    @ApiModelProperty("是否锁定")
    private Boolean locked;
}
