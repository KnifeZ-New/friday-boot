package org.knifez.fridaybootadmin.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.knifez.fridaybootadmin.entity.AppRole;
import org.knifez.fridaybootadmin.entity.AppUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

/**
 * @author KnifeZ
 */
@Getter
@Setter
@Schema(title = "AppUserDTO")
public class AppUserDTO extends AppUser {
    @Schema(title = "所属部门")
    private String organizationName;

    @Schema(title = "角色")
    private List<AppRole> roles = new ArrayList<>();

    @TableField(exist = false)
    @JsonIgnore
    @Schema(title = "角色")
    private List<String> userRoles = new ArrayList<>();

    @TableField(exist = false)
    @JsonIgnore
    @Schema(title = "权限")
    private List<String> permissions = new ArrayList<>();

    public List<SimpleGrantedAuthority> getGrantRoles() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        userRoles.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role)));
        if (authorities.isEmpty()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_GUEST"));
        }
        return authorities;
    }
}
