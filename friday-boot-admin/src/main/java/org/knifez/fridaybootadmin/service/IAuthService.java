package org.knifez.fridaybootadmin.service;

import org.knifez.fridaybootadmin.dto.LoginRequest;
import org.knifez.fridaybootadmin.dto.Token;
import org.knifez.fridaybootadmin.entity.AppUser;

/**
@author KnifeZ
 */
public interface IAuthService {
    /**
     * 创建令牌
     *
     * @param loginRequest 登录请求
     * @return {@link Token}
     */
    Token createToken(LoginRequest loginRequest);

    /**
     * 删除令牌
     */
    void removeToken();

    /**
     * 获取当前用户
     *
     * @return {@link AppUser}
     */
    AppUser getCurrentUser();

    /**
     * 获得当前用户名
     *
     * @return {@link String}
     */
    String getCurrentUserName();

    /**
     * 获得当前用户id
     *
     * @return {@link String}
     */
    String getCurrentUserId();
}
