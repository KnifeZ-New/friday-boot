package org.knifez.fridaybootadmin.dto;

import cn.hutool.json.JSONUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author KnifeZ
 */
@Data
@Schema(title = "JwtUserDetail")
public class JwtUserDetail implements UserDetails {
    /**
     * id
     */
    private Integer id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 锁定
     */
    private Boolean locked;

    /**
     * 组织id
     */
    private Integer organizationId;

    /**
     * 角色
     */
    private List<String> roles;


    /**
     * 数据权限
     */
    private List<Integer> dataPermissions;

    /**
     * 权限
     */
    private List<String> permissions;


    public JwtUserDetail(AppUserInfoDTO user) {
        id = user.getId();
        username = user.getUsername();
        locked = user.getLocked() == null || user.getLocked();
        organizationId = user.getOrganizationId();
        roles = user.getUserRoles();
        permissions = user.getPermissions();
        dataPermissions = user.getDataPermissions();
    }

    public JwtUserDetail(String jsonObj) {
        var user = JSONUtil.parse(jsonObj).toBean(AppUserInfoDTO.class);
        id = user.getId();
        username = user.getUsername();
        locked = user.getLocked() == null || user.getLocked();
        organizationId = user.getOrganizationId();
        roles = user.getUserRoles();
        permissions = user.getPermissions();
        dataPermissions = user.getDataPermissions();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        if (permissions != null) {
            permissions.forEach(permission -> grantedAuthorities.add(new SimpleGrantedAuthority(permission)));
        }
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !this.locked;
    }

    public String toJsonString() {
        return JSONUtil.toJsonStr(this);
    }

}
