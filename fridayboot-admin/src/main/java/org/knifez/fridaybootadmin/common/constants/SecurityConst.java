package org.knifez.fridaybootadmin.common.constants;


/**
 * @author KnifeZ
 */
public class SecurityConst {
    private SecurityConst() {
        throw new IllegalStateException("SecurityConst class");
    }


    /**
     * 超级管理员
     */
    public static final String ROLE_SUPER_ADMIN = "ROLE_SuperAdmin";

    public static final String ACCOUNT="Account";

    public static final String USER_NAME="Username";
    public static final String USER_ROLES="UserRoles";
    public static final String USER_DATA_PERMISSIONS="DataPermissions";
    public static final String ROLE_LIST="RoleList";
    public static final String ROLE_PERMISSION_LIST="PermissionList";




    public static final String JWT_TOKEN_HEADER = "Authorization";

    public static final String JWT_TOKEN_PREFIX = "Bearer ";
    /**
     * 角色声明
     */
    public static final String ROLE_CLAIMS = "rol";
    /**
     * rememberMe 为 false 的时候过期时间是24个小时
     */
    public static final Long EXPIRATION = 60 * 60 * 24 * 3L;

    /**
     * rememberMe 为 true 的时候过期时间是7天
     */
    public static final Long EXPIRATION_REMEMBER = 60 * 60 * 24 * 7L;

    /**
     * 最大客户端数量
     */
    public static final Integer MAX_CLIENT_COUNT = 3;

}
