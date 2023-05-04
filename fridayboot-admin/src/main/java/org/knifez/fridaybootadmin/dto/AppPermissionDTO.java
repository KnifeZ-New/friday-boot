package org.knifez.fridaybootadmin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AppPermissionDTO {
    @Schema(title = "接口权限列表")
    private List<String> ApiPermissions;
    @Schema(title = "前端按钮权限列表")
    private List<String> WebPermissions;
}
