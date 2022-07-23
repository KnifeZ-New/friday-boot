package com.knifez.fridaybootadmin.service.impl;

import com.knifez.fridaybootadmin.entity.AppUser;
import com.knifez.fridaybootadmin.dto.JwtUser;
import com.knifez.fridaybootadmin.dto.LoginRequest;
import com.knifez.fridaybootadmin.dto.Token;
import com.knifez.fridaybootadmin.service.IAppUserService;
import com.knifez.fridaybootadmin.service.IAuthService;
import com.knifez.fridaybootadmin.utils.JwtTokenUtils;
import com.knifez.fridaybootcore.enums.ResultStatus;
import com.knifez.fridaybootcore.exception.FridayResultException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhang
 */
@Service
public class AuthServiceImpl implements IAuthService {
    private final IAppUserService userService;
    private final StringRedisTemplate stringRedisTemplate;

    public AuthServiceImpl(IAppUserService userService, StringRedisTemplate stringRedisTemplate) {
        this.userService = userService;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public Token createToken(LoginRequest loginRequest) {
        AppUser user = userService.findByAccount(loginRequest.getUsername());
        if (!JwtTokenUtils.checkLogin(loginRequest.getPassword(), user.getPassword())) {
            throw new FridayResultException(ResultStatus.UNAUTHORIZED_001);
        }
        JwtUser jwtUser = new JwtUser(user);
        if (!jwtUser.isEnabled()) {
            throw new FridayResultException(ResultStatus.FORBIDDEN_001);
        }
        List<String> authorities = jwtUser.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        Token token = JwtTokenUtils.createToken(user.getAccount(), user.getId().toString(), authorities, loginRequest.getRememberMe());
        stringRedisTemplate.opsForValue().set(user.getId().toString(), token.getAccessToken());
        return token;
    }

    public void removeToken() {
        stringRedisTemplate.delete(getCurrentUser().getId().toString());
    }

    /**
     * 获取当前用户
     *
     * @return {@link AppUser}
     */
    @Override
    public AppUser getCurrentUser() {
        return userService.findByAccount(getCurrentUserName());
    }

    /**
     * 获得当前用户名
     *
     * @return {@link String}
     */
    @Override
    public String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null) {
            return (String) authentication.getPrincipal();
        }
        return null;
    }

    /**
     * 获得当前用户id
     *
     * @return {@link String}
     */
    @Override
    public String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        var token= JWTUtil.parseToken()
        if (authentication != null && authentication.getPrincipal() != null) {
            return (String) authentication.getPrincipal();
        }
        return null;
    }

}
