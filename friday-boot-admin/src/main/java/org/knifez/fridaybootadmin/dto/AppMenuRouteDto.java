package org.knifez.fridaybootadmin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AppMenuRouteDto {
    @Schema(defaultValue = "name")
    private String name;
    @Schema(defaultValue = "组件")
    private String component;

    @Schema(defaultValue = "meta")
    private RouteMeta meta;

    @Schema(defaultValue = "props")
    private List<String> props;
    @Schema(defaultValue = "子路由")
    private List<AppMenuRouteDto> children;
}
