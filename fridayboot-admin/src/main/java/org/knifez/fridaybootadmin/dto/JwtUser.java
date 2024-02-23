package org.knifez.fridaybootadmin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @author KnifeZ
 */
@Data
@Schema(title = "JwtUser")
public class JwtUser implements UserDetails {
    /**
     * id
     */
    private Long id;
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
     * 角色
     */
    private List<String> roles;

    /**
     * 权限
     */
    private Collection<? extends GrantedAuthority> authorities;

    public JwtUser(AppUserInfoDTO user) {
        id = user.getId();
        username = user.getUsername();
        password = user.getPassword();
        locked = user.getLocked() == null || user.getLocked();
        roles = user.getUserRoles();
        authorities = user.getGrantAuthorities();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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


    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", authorities=" + authorities +
                '}';
    }

}
