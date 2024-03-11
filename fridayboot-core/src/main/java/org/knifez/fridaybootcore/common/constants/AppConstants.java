package org.knifez.fridaybootcore.common.constants;

/**
 * 应用常量
 *
 * @author KnifeZ
 */
public class AppConstants {
    /**
     * api前缀
     */
    public static final String API_PREFIX = "/api";
    /**
     * api版本
     */
    public static final String API_VERSION = "v1";
    /**
     * 当前用户权限前缀
     */
    public static final String CURRENT_USER_PERMISSION_PREFIX = "permissions_for_";

    /**
     * 当前用户信息DTO
     */
    public static final String CURRENT_USERINFO_DTO = "userinfo_";
    /**
     * 超级管理员
     */
    public static final String ROLE_SUPER_ADMIN = "ROLE_SuperAdmin";
    /**
     * 包前缀
     */
    public static final String PACKAGE_PREFIX = "org.knifez.fridayboot";
    /**
     * Redis前缀文件夹
     */
    public static final String REDIS_PREFIX_FOLDER = "fridayboot:";
    public static final String JWT_TOKEN_HEADER = "Authorization";
    public static final String JWT_TOKEN_PREFIX = "Bearer ";
    public static final String JWT_TOKEN_TYPE = "JWT";
    public static final String JWT_SECRET_KEY = "C*F-JaNdRgUkXn2r5u8x/A?D(G+KbPeShVmYq3s6v9y$B&E)H@McQfTjWnZr4u7w";

    private AppConstants() {
        throw new IllegalStateException("AppConstants class");
    }
}
