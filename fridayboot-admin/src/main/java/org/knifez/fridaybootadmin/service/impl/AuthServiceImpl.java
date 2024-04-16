package org.knifez.fridaybootadmin.service.impl;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.session.SaSessionCustomUtil;
import cn.dev33.satoken.stp.SaLoginConfig;
import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.knifez.fridaybootadmin.common.constants.SecurityConst;
import org.knifez.fridaybootadmin.dto.AppUserInfoDTO;
import org.knifez.fridaybootadmin.dto.LoginRequest;
import org.knifez.fridaybootadmin.dto.Token;
import org.knifez.fridaybootadmin.service.IAppPermissionGrantService;
import org.knifez.fridaybootadmin.service.IAppUserService;
import org.knifez.fridaybootadmin.service.IAuthService;
import org.knifez.fridaybootadmin.utils.JwtTokenUtils;
import org.knifez.fridaybootcore.common.enums.ResultStatus;
import org.knifez.fridaybootcore.common.exception.FridayResultException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author KnifeZ
 */
@Slf4j
@Service
public class AuthServiceImpl implements IAuthService, StpInterface {
    private final IAppUserService userService;
    private final IAppPermissionGrantService permissionGrantService;

    public AuthServiceImpl(IAppUserService userService, IAppPermissionGrantService permissionGrantService) {
        this.userService = userService;
        this.permissionGrantService = permissionGrantService;
    }

    public Token createToken(LoginRequest loginRequest) {
        AppUserInfoDTO user = userService.findByAccount(loginRequest.getUsername());
        if (!JwtTokenUtils.checkLogin(loginRequest.getPassword(), user.getPassword())) {
            throw new FridayResultException(ResultStatus.UNAUTHORIZED_001);
        }
        if (user.getLocked()) {
            throw new FridayResultException(ResultStatus.FORBIDDEN_001);
        }
        StpUtil.login(user.getId(), SaLoginConfig
                .setExtra(SecurityConst.USER_NAME, user.getUsername())
                .setExtra(SecurityConst.ACCOUNT, user.getAccount())
                .setExtra(SecurityConst.USER_DATA_PERMISSIONS, user.getDataPermissions())
                .setExtra(SecurityConst.USER_ROLES, user.getUserRoles()));

        return new Token(StpUtil.getTokenValue(), StpUtil.getTokenTimeout(), "", 0L);
    }

    public void removeToken() {
        StpUtil.logoutByTokenValue(JwtTokenUtils.getJWTToken());
    }

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        // 1. 声明权限码集合
        List<String> permissionList = new ArrayList<>();
        // 2. 遍历角色列表，查询拥有的权限码
        for (String roleId : getRoleList(loginId, loginType)) {
            SaSession roleSession = SaSessionCustomUtil.getSessionById("ROLE_" + roleId);
            List<String> list = roleSession.get(SecurityConst.ROLE_PERMISSION_LIST, () -> {
                return permissionGrantService.getApiPermissionList(roleId); // 从数据库查询这个角色所拥有的权限列表
            });
            permissionList.addAll(list);
        }

        // 3. 返回权限码集合
        return permissionList.stream().distinct().toList();
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        SaSession session = StpUtil.getSessionByLoginId(loginId);
        return session.get(SecurityConst.ROLE_LIST, () -> {
            var token = StpUtil.getTokenValueByLoginId(loginId);
            return StpUtil.getExtra(token, SecurityConst.USER_ROLES);
        });
    }
}
