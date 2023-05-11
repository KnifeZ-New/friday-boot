package org.knifez.fridaybootadmin.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

/**
 * @author KnifeZ
 */
@Getter
@Setter
@ToString
@Schema(title = "AppUserInfoDTO")
public class AppUserInfoDTO {
    
    @JsonIgnore
    @Schema(title = "主键id")
    private Long id;

    @Schema(title = "账号")
    private String account;

    @Schema(title = "姓名")
    private String username;

    @JsonIgnore
    @Schema(title = "密码")
    private String password;

    @Schema(title = "邮箱")
    private String email;

    @Schema(title = "电话")
    private String phone;

    @Schema(title = "头像")
    private String avatar;

    @Schema(title = "是否启锁定")
    private Boolean locked;

    @Schema(title = "所属部门id")
    private Long organizationId;

    @Schema(title = "所属部门")
    private String organizationName;

    @Schema(title = "用户首页")
    private String homePath;

    @Schema(title = "角色")
    private List<String> userRoles = new ArrayList<>();

    @Schema(title = "按钮权限")
    private List<String> permissions = new ArrayList<>();

    @JsonIgnore
    @Schema(title = "权限标识")
    private List<String> apiPermissions = new ArrayList<>();

    public List<SimpleGrantedAuthority> getGrantRoles() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        userRoles.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role)));
        if (authorities.isEmpty()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_GUEST"));
        }
        return authorities;
    }
}
