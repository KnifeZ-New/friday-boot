package org.knifez.fridaybootadmin.service.impl;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.knifez.fridaybootadmin.dto.AppUserInfoDTO;
import org.knifez.fridaybootadmin.dto.JwtUser;
import org.knifez.fridaybootadmin.dto.LoginRequest;
import org.knifez.fridaybootadmin.dto.Token;
import org.knifez.fridaybootadmin.service.IAppUserService;
import org.knifez.fridaybootadmin.service.IAuthService;
import org.knifez.fridaybootadmin.utils.JwtTokenUtils;
import org.knifez.fridaybootcore.common.constants.AppConstants;
import org.knifez.fridaybootcore.common.enums.ResultStatus;
import org.knifez.fridaybootcore.common.exception.FridayResultException;
import org.knifez.fridaybootcore.utils.JwtUtils;
import org.knifez.fridaybootcore.utils.RedisUtils;
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
        JwtUser jwtUser = new JwtUser(user);
        if (!jwtUser.isEnabled()) {
            throw new FridayResultException(ResultStatus.FORBIDDEN_001);
        }
        var redisKey = RedisUtils.formatKey(user.getAccount());
        Token token = JwtTokenUtils.createToken(user.getAccount(), user.getId().toString(), user.getUserRoles().stream().map(x -> "ROLE_" + x).toList(), user.getPermissions(), loginRequest.getRememberMe());
        //重复登录accessToken不变，防止帐号挤掉
        log.info(redisKey + "登录");
        if (Boolean.TRUE.equals(redisTemplate.hasKey(redisKey))) {
            //验证老token是否过期
            log.warn("userKey重复：" + redisKey);
            var userRedisValue = redisTemplate.opsForValue().get(redisKey);
            if (!JwtUtils.isExpired(userRedisValue)) {
                token.setAccessToken(userRedisValue);
            }
        }
        redisTemplate.opsForValue().set(redisKey, token.getAccessToken(), token.getExpires(), TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(RedisUtils.formatKey(AppConstants.CURRENT_USER_PERMISSION_PREFIX + user.getAccount()), JSONUtil.toJsonStr(user.getPermissions()), token.getExpires(), TimeUnit.SECONDS);
        return token;
    }

    public void removeToken() {
        redisTemplate.delete(RedisUtils.formatKey(JwtTokenUtils.getCurrentUserAccount()));
    }

}
