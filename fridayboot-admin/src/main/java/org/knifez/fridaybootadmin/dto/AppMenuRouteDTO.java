package org.knifez.fridaybootadmin.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(title = "AppMenuRouteDTO")
public class AppMenuRouteDTO {
    @Schema(title = "路由地址")
    private String path;
    @Schema(title = "name")
    private String name;
    @Schema(title = "组件")
    private String component;

    @Schema(title = "meta")
    private RouteMeta meta;

    @JsonIgnore
    @Schema(title = "props")
    private List<String> props;
    @Schema(title = "子路由")
    private List<AppMenuRouteDTO> children;
}
