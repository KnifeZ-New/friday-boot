package org.knifez.fridaybootadmin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.knifez.fridaybootcore.dto.PageRequest;

import java.util.List;

/**
@author KnifeZ
 */
@Getter
@Setter
public class AppUserPagedQueryRequest extends PageRequest {

    @NotNull(message = "name 不能为空")
    @Schema(defaultValue = "用户姓名")
    private String username;

    @Schema(defaultValue = "部门")
    private List<Long> organizationIds;

    @Schema(defaultValue = "是否锁定")
    private Boolean locked;
}
