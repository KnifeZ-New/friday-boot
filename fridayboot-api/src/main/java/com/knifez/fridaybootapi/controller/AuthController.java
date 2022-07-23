package com.knifez.fridaybootapi.controller;

import com.knifez.fridaybootadmin.dto.LoginRequest;
import com.knifez.fridaybootadmin.dto.Token;
import com.knifez.fridaybootadmin.service.IAuthService;
import com.knifez.fridaybootcore.annotation.ApiRestController;
import com.knifez.fridaybootcore.annotation.permission.AllowAnonymous;
import com.knifez.fridaybootcore.dto.FridayResult;
import com.knifez.fridaybootcore.entity.ApplicationCollocation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @author zhang
 */
@Api(tags = "认证")
@ApiRestController
@RequestMapping("/auth")
public class AuthController {
    private final IAuthService authService;

    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @AllowAnonymous
    @ApiOperation("登录")
    @PostMapping("login")
    public Token login(@RequestBody LoginRequest loginRequest) {
        return authService.createToken(loginRequest);
    }

    /**
     * 应用程序配置
     *
     * @return {@link FridayResult}<{@link ApplicationCollocation}>
     */
    @GetMapping("application-configuration")
    @ApiOperation("系统配置")
    public ApplicationCollocation applicationCollocation() {
        var applicationCollocation = new ApplicationCollocation();
        applicationCollocation.setLanguage("zh_CN");
        applicationCollocation.setUserId(authService.getCurrentUserId());
        return applicationCollocation;
    }

    @ApiOperation("退出")
    @PostMapping("logout")
    public void logout() {
        authService.removeToken();
    }

}
