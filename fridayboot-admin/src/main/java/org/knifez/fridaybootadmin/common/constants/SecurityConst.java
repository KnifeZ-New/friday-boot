package org.knifez.fridaybootadmin.common.constants;


/**
 * @author KnifeZ
 */
public class SecurityConst {
    private SecurityConst() {
        throw new IllegalStateException("SecurityConst class");
    }


    /**
     * 角色声明
     */
    public static final String ROLE_CLAIMS = "rol";
    /**
     * rememberMe 为 false 的时候过期时间是24个小时
     */
    public static final long EXPIRATION = 60 * 60L * 24;

    /**
     * rememberMe 为 true 的时候过期时间是7天
     */
    public static final long EXPIRATION_REMEMBER = 60 * 60 * 24 * 7L;

    /**
     * 最大客户端数量
     */
    public static final Integer MAX_CLIENT_COUNT = 5;

}
