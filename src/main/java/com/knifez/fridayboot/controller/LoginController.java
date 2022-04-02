package com.knifez.fridayboot.controller;

import com.knifez.fridayboot.model.HttpResult;
import com.knifez.fridayboot.model.LoginBean;
import com.knifez.fridayboot.security.JwtAuthenticationToken;
import com.knifez.fridayboot.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author KnifeZ
 */
@Api(tags = "登录")
@RestController
@RequiredArgsConstructor
public class LoginController {
    private final AuthenticationManager authenticationManager;


    /**
     * 登录接口
     */
    @PermitAll
    @ApiOperation("登录")
    @PostMapping("login")
    public HttpResult login(@RequestBody LoginBean loginBean, HttpServletRequest request) throws IOException {
        String account = loginBean.getAccount();
        String password = loginBean.getPassword();

        // 系统登录认证
        JwtAuthenticationToken token = SecurityUtils.login(request, account, password, authenticationManager);

        return HttpResult.ok(token);
    }

    @ApiOperation("退出登陆")
    @PostMapping("logout")
    public void logout(){

    }
}
