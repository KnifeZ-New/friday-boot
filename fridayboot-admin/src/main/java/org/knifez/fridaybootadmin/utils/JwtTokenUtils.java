package org.knifez.fridaybootadmin.utils;

import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.knifez.fridaybootadmin.common.constants.SecurityConst;
import org.knifez.fridaybootcore.utils.ServletRequestUtils;

import java.util.*;

/**
 * @author KnifeZ
 */
@Slf4j
public class JwtTokenUtils {


    private JwtTokenUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean checkLogin(String currentPassword, String password) {
        return BCrypt.checkpw(currentPassword, password);
    }

    public static boolean isSuperAdmin() {
        return StpUtil.getRoleList().contains(SecurityConst.ROLE_SUPER_ADMIN);
    }

    public static String getCurrentUser() {
        return StpUtil.getExtra(SecurityConst.ACCOUNT).toString();
    }

    public static String getJWTToken() {
        var token = ServletRequestUtils.getRequest().getHeader(SecurityConst.JWT_TOKEN_HEADER);
        if (token == null) return null;
        token = fixToken(token);
        return token;
    }


    /**
     * 获取数据权限
     *
     * @return {@link List}<{@link Integer}>
     */
    public static List<Integer> getDataPermission(List<Integer> selected) {
        List<Integer> result = new ArrayList<>();
        List<Integer> dataPermissions = new ArrayList<>();
        Object obj = StpUtil.getExtra(SecurityConst.USER_DATA_PERMISSIONS); // 假设这里有一个Object类型的对象
        if (obj instanceof List<?> rawList) {
            dataPermissions = (List<Integer>) rawList;
        }
        if (selected != null && !selected.isEmpty()) {
            result.addAll(selected);
            // 移除不在数据权限列表的数据,超管不移除
            if (!JwtTokenUtils.isSuperAdmin()) {
                List<Integer> finalDataPermissions = dataPermissions;
                result.removeIf(item -> finalDataPermissions.stream().noneMatch(x -> x.equals(item)));
            }
        } else {
            result.addAll(dataPermissions);
        }
        if (JwtTokenUtils.isSuperAdmin()) {
            return result;
        }
        // 非超管且无数据权限
        if (result.isEmpty()) {
            result.add(0);
        }
        return result;
    }

    private static String fixToken(String token) {
        return token.replace(SecurityConst.JWT_TOKEN_PREFIX, "");
    }
}

