package org.knifez.fridaybootadmin.service;

import org.knifez.fridaybootadmin.dto.LoginRequest;
import org.knifez.fridaybootadmin.dto.Token;

/**
 * @author KnifeZ
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
}
