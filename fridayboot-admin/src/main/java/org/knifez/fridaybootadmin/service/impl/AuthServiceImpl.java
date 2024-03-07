package org.knifez.fridaybootadmin.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.knifez.fridaybootadmin.common.constants.SecurityConst;
import org.knifez.fridaybootadmin.dto.AppUserInfoDTO;
import org.knifez.fridaybootadmin.dto.LoginRequest;
import org.knifez.fridaybootadmin.dto.Token;
import org.knifez.fridaybootadmin.service.IAppUserService;
import org.knifez.fridaybootadmin.service.IAuthService;
import org.knifez.fridaybootadmin.utils.JwtTokenUtils;
import org.knifez.fridaybootcore.common.constants.AppConstants;
import org.knifez.fridaybootcore.common.enums.ResultStatus;
import org.knifez.fridaybootcore.common.exception.FridayResultException;
import org.knifez.fridaybootadmin.utils.RedisUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author KnifeZ
 */
@Slf4j
@Service
public class AuthServiceImpl implements IAuthService {
    private final IAppUserService userService;
    private final StringRedisTemplate redisTemplate;

    public AuthServiceImpl(IAppUserService userService, StringRedisTemplate redisTemplate) {
        this.userService = userService;
        this.redisTemplate = redisTemplate;
    }

    public Token createToken(LoginRequest loginRequest) {
        AppUserInfoDTO user = userService.findByAccount(loginRequest.getUsername());
        if (!JwtTokenUtils.checkLogin(loginRequest.getPassword(), user.getPassword())) {
            throw new FridayResultException(ResultStatus.UNAUTHORIZED_001);
        }
        if (user.getLocked()) {
            throw new FridayResultException(ResultStatus.FORBIDDEN_001);
        }
        Token token = JwtTokenUtils.createToken(user.getAccount(), user.getId().toString(), user.getUserRoles().stream().map(x -> "ROLE_" + x).toList(), loginRequest.getRememberMe());
        log.debug(user.getAccount() + "登录");
        var loginTokens = RedisUtils.getUserToken(redisTemplate, user.getAccount());
        if (loginTokens.size() >= SecurityConst.MAX_CLIENT_COUNT) {
            throw new FridayResultException(ResultStatus.UNAUTHORIZED_003);
        }
        // 记录登录用户
        RedisUtils.setUserToken(redisTemplate, user.getAccount(), token.getAccessToken(), true);
        // 记录用户权限
        RedisUtils.setStr(redisTemplate, AppConstants.CURRENT_USER_PERMISSION_PREFIX + user.getAccount(),
                String.join(",", user.getPermissions()), token.getExpires(), TimeUnit.SECONDS);
        return token;
    }

    public void removeToken() {
        RedisUtils.setUserToken(redisTemplate, JwtTokenUtils.getCurrentUserAccount(), AppConstants.JWT_TOKEN_PREFIX + JwtTokenUtils.getJWTToken(), false);
    }

}
