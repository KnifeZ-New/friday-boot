package com.knifez.fridaybootapi.controller;

import com.knifez.fridaybootadmin.dto.AppUserInfoResponse;
import com.knifez.fridaybootadmin.dto.LoginRequest;
import com.knifez.fridaybootadmin.dto.Token;
import com.knifez.fridaybootadmin.service.IAuthService;
import com.knifez.fridaybootcore.annotation.ApiRestController;
import com.knifez.fridaybootcore.annotation.permission.AllowAnonymous;
import com.knifez.fridaybootcore.annotation.permission.AllowAuthenticated;
import com.knifez.fridaybootcore.constants.AppConstants;
import com.knifez.fridaybootcore.entity.ApplicationCollocation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author zhang
 */
@Api(tags = "认证")
@ApiRestController
@RequestMapping("/auth")
public class AuthController {
    private final IAuthService authService;

    private final WebApplicationContext webApplicationContext;

    public AuthController(IAuthService authService, WebApplicationContext webApplicationContext) {
        this.authService = authService;
        this.webApplicationContext = webApplicationContext;
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
     * @return {@link ApplicationCollocation}
     */
    @AllowAuthenticated
    @GetMapping("application-configuration")
    @ApiOperation("系统配置")
    public ApplicationCollocation applicationCollocation() {
        // 系统配置表读取配置
        var applicationCollocation = new ApplicationCollocation();
        //默认语言
        applicationCollocation.setLanguage("zh_CN");
        //暗黑模式
        applicationCollocation.setDarkMode(true);
        //系统所有权限
        List<String> policies = new ArrayList<>();
        
        RequestMappingHandlerMapping mapping = webApplicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> methodMap = mapping.getHandlerMethods();
        methodMap.forEach((requestMappingInfo, handlerMethod) -> {
            if (handlerMethod.toString().startsWith(AppConstants.PACKAGE_PREFIX)) {
                policies.add(handlerMethod.getBeanType().getName().substring(handlerMethod.getBeanType().getPackageName().length() + 1) + "." + handlerMethod.getMethod().getName());
            }
        });
        applicationCollocation.setGrantedPolicies(policies);

        return applicationCollocation;
    }

    /**
     * 获取当前用户信息
     *
     * @return {@link AppUserInfoResponse}
     */
    @AllowAuthenticated
    @GetMapping("current-userinfo")
    @ApiOperation("当前用户信息")
    public AppUserInfoResponse getCurrentUserInfo() {
        var userInfo = new AppUserInfoResponse();
        var currentUser = authService.getCurrentUser();
        BeanUtils.copyProperties(currentUser, userInfo);
        userInfo.setHomePath("/");
        return userInfo;
    }

    @AllowAuthenticated
    @ApiOperation("退出")
    @PostMapping("logout")
    public void logout() {
        authService.removeToken();
    }

}
