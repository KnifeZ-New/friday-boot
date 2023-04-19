package org.knifez.fridaybootadmin.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(title = "RouteMeta")
public class RouteMeta {
    @Schema(title = "标题")
    private String title;
    @Schema(title = "图标")
    private String icon;
    @Schema(title = "排序")
    private Integer orderNo;

    @Schema(title = "隐藏菜单")
    private Boolean hideMenu;
    @Schema(title = "是否缓存页面")
    private Boolean ignoreKeepAlive;
    @Schema(title = "页面权限")
    private String grantedPolicy;

    @JsonIgnore
    @Schema(title = "菜单动效")
    private String transitionName;

    @Schema(title = "忽略权限")
    private Boolean ignoreAuth;

//    @Schema(title = "排序")
//    private Integer dynamicLevel;


//    private Boolean affix;

//    private Boolean hideTab;
//
//    private Boolean hideBreadcrumb;
//
//    private Boolean hideChildrenInMenu;
//
//    private Boolean hidePathForChildren;
//
//    private Boolean carryParam;
//
//    private Boolean single;
//
//    private String currentActiveMenu;
//
//    private List<String> roles;
//
//    private Boolean isLink;
//
//    private String realPath;
//
//    private String frameSrc;

}
