package com.knifez.fridayboot.security.controller;

import com.knifez.fridayboot.domain.dto.FridayResponse;
import com.knifez.fridayboot.security.dto.LoginRequest;
import com.knifez.fridayboot.security.dto.Token;
import com.knifez.fridayboot.security.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhang
 */
@Api(tags = "认证")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    @ApiOperation("登录")
    public FridayResponse<Token> login(@RequestBody LoginRequest loginRequest) {
        Token token = authService.createToken(loginRequest);
        return FridayResponse.success(token);
    }

    @PostMapping("/logout")
    @ApiOperation("退出")
    public FridayResponse<Void> logout() {
        authService.removeToken();
        return FridayResponse.success();
    }

}
