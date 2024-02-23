package org.knifez.fridaybootadmin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.knifez.fridaybootcore.dto.PagedRequest;

import java.util.List;

/**
 * @author KnifeZ
 */
@Getter
@Setter
@Schema(title = "AppUserPagedRequest")
public class AppUserPagedRequest extends PagedRequest {

    @NotNull(message = "用户姓名不能为空")
    @Schema(title = "用户姓名")
    private String username;

    @Schema(title = "部门")
    private List<Long> organizationIds;

    @Schema(title = "是否锁定")
    private Boolean locked;
}
