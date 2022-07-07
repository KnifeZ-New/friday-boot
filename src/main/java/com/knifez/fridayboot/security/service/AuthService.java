package com.knifez.fridayboot.security.service;

import com.knifez.fridayboot.entity.AppUser;
import com.knifez.fridayboot.security.dto.LoginRequest;
import com.knifez.fridayboot.security.dto.Token;
import com.knifez.fridayboot.security.entity.JwtUser;
import com.knifez.fridayboot.security.utils.CurrentUserUtils;
import com.knifez.fridayboot.security.utils.JwtTokenUtils;
import com.knifez.fridayboot.service.IAppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhang
 */
@Service
@RequiredArgsConstructor
public class AuthService {
    private final IAppUserService userService;
    private final StringRedisTemplate stringRedisTemplate;
    private final CurrentUserUtils currentUserUtils;

    public Token createToken(LoginRequest loginRequest) {
        AppUser user = userService.findByAccount(loginRequest.getUsername());
        if (!currentUserUtils.checkLogin(loginRequest.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"The user name or password is not correct.");
        }
        JwtUser jwtUser = new JwtUser(user);
        if (!jwtUser.isEnabled()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"User is forbidden to login");
        }
        List<String> authorities = jwtUser.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        Token token = JwtTokenUtils.createToken(user.getAccount(), user.getId().toString(), authorities, loginRequest.getRememberMe());
        stringRedisTemplate.opsForValue().set(user.getId().toString(), token.accessToken);
        return token;
    }

    public void removeToken() {
        stringRedisTemplate.delete(currentUserUtils.getCurrentUser().getId().toString());
    }
}
