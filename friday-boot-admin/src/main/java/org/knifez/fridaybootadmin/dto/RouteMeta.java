package org.knifez.fridaybootadmin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RouteMeta {
    @Schema(defaultValue = "排序")
    private Integer orderNo;
    @Schema(defaultValue = "标题")
    private String title;
    @Schema(defaultValue = "排序")
    private Integer dynamicLevel;

    private String realPath;
    private Boolean ignoreAuth;

    private String grantedPolicy;

    private List<String> roles;

    private Boolean ignoreKeepAlive;

    private Boolean affix;

    private String icon;

    private String frameSrc;

    private String transitionName;

    private Boolean hideBreadcrumb;

    private Boolean hideChildrenInMenu;

    private Boolean carryParam;

    private Boolean single;

    private String currentActiveMenu;

    private Boolean hideTab;

    private Boolean hideMenu;

    private Boolean isLink;

    private Boolean ignoreRoute;

    private Boolean hidePathForChildren;


}
