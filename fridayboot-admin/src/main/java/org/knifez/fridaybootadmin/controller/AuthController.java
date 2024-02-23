package org.knifez.fridaybootadmin.controller;


import cn.hutool.system.SystemUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.knifez.fridaybootadmin.dto.AppUserInfoDTO;
import org.knifez.fridaybootadmin.dto.LoginRequest;
import org.knifez.fridaybootadmin.dto.Token;
import org.knifez.fridaybootadmin.service.IAppPermissionGrantService;
import org.knifez.fridaybootadmin.service.IAppUserService;
import org.knifez.fridaybootadmin.service.IAuthService;
import org.knifez.fridaybootadmin.utils.JwtTokenUtils;
import org.knifez.fridaybootcore.annotation.ApiRestController;
import org.knifez.fridaybootcore.annotation.permission.AllowAnonymous;
import org.knifez.fridaybootcore.annotation.permission.AllowAuthenticated;
import org.knifez.fridaybootcore.constants.AppConstants;
import org.knifez.fridaybootcore.entity.ApplicationCollocation;
import org.knifez.fridaybootcore.utils.JwtUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author KnifeZ
 */
@Slf4j
@Tag(name = "认证")
@ApiRestController
@RequestMapping("/auth")
public class AuthController {

    private final IAppPermissionGrantService permissionGrantService;
    private final IAuthService authService;

    private final IAppUserService userService;

    private final WebApplicationContext webApplicationContext;


    public AuthController(IAppPermissionGrantService permissionGrantService, IAuthService authService, IAppUserService userService, WebApplicationContext webApplicationContext) {
        this.permissionGrantService = permissionGrantService;

        this.authService = authService;
        this.userService = userService;
        this.webApplicationContext = webApplicationContext;
    }

    @AllowAnonymous
    @Operation(summary = "登录")
    @PostMapping("login")
    public Token login(@RequestBody LoginRequest loginRequest) {
        return authService.createToken(loginRequest);
    }

    @AllowAnonymous
    @Operation(summary = "退出")
    @PostMapping("logout")
    public void logout() {
        authService.removeToken();
    }

    @AllowAnonymous
    @Operation(summary = "重启服务")
    @GetMapping("reboot")
    public void reboot() throws IOException {
        log.info("springboot reboot");
        // 执行重启shell脚本
        String projectPath = System.getProperty("user.dir");
        var file = new File(projectPath);
        if (SystemUtil.getOsInfo().isLinux()) {
            Runtime.getRuntime().exec("sh restart.sh", new String[]{}, file);
        }
        if (SystemUtil.getOsInfo().isWindows()) {
            Runtime.getRuntime().exec(".\\restart.bat", new String[]{}, file);
        }
    }

    /**
     * 应用程序配置
     *
     * @return {@link ApplicationCollocation}
     */
    @AllowAuthenticated
    @GetMapping("application-configuration")
    @Operation(summary = "系统配置")
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
     * @return {@link AppUserInfoDTO}
     */
    @AllowAuthenticated
    @GetMapping("current-userinfo")
    @Operation(summary = "当前用户信息")
    public AppUserInfoDTO getCurrentUserInfo() {
        var userInfo = userService.findByAccount(JwtUtils.getCurrentUser());
        userInfo.setHomePath("/");
        var menus = permissionGrantService.getUserMenuByPermissions(userInfo.getPermissions(), JwtTokenUtils.isSuperAdmin());
        userInfo.setMenu(menus);
        return userInfo;
    }

}
