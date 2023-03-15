package org.knifez.fridaybootadmin.service.impl;

import cn.hutool.json.JSONUtil;
import org.knifez.fridaybootadmin.dto.AppUserDTO;
import org.knifez.fridaybootadmin.dto.JwtUser;
import org.knifez.fridaybootadmin.dto.LoginRequest;
import org.knifez.fridaybootadmin.dto.Token;
import org.knifez.fridaybootadmin.entity.AppUser;
import org.knifez.fridaybootadmin.service.IAppUserService;
import org.knifez.fridaybootadmin.service.IAuthService;
import org.knifez.fridaybootadmin.utils.JwtTokenUtils;
import org.knifez.fridaybootadmin.utils.RedisUtils;
import org.knifez.fridaybootcore.constants.AppConstants;
import org.knifez.fridaybootcore.enums.ResultStatus;
import org.knifez.fridaybootcore.exception.FridayResultException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author KnifeZ
 */
@Service
public class AuthServiceImpl implements IAuthService {
    private final IAppUserService userService;
    private final RedisUtils redisUtils;

    public AuthServiceImpl(IAppUserService userService, RedisUtils redisUtils) {
        this.userService = userService;
        this.redisUtils = redisUtils;
    }

    public Token createToken(LoginRequest loginRequest) {
        AppUserDTO user = userService.findByAccount(loginRequest.getUsername());
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
        //重复登录accessToken不变，防止帐号挤掉
        if (Boolean.TRUE.equals(redisUtils.hasKey(user.getAccount()))) {
            //验证老token是否过期
            if (!JwtTokenUtils.isExpired(redisUtils.get(user.getAccount()))) {
                token.setAccessToken(redisUtils.get(user.getAccount()));
            }
        }

        redisUtils.set(user.getAccount(), token.getAccessToken(), token.getExpires(), TimeUnit.SECONDS);
        redisUtils.set(AppConstants.CURRENT_USER_PERMISSION_PREFIX + user.getAccount(), JSONUtil.toJsonStr(user.getPermissions()), token.getExpires(), TimeUnit.SECONDS);
        return token;
    }

    public void removeToken() {
        redisUtils.delete(getCurrentUser().getAccount());
    }

    /**
     * 获取当前用户
     *
     * @return {@link AppUser}
     */
    @Override
    public AppUserDTO getCurrentUser() {
        return userService.findByAccount(JwtTokenUtils.getCurrentUserName());
    }

}
