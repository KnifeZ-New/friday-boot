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
     * 包前缀
     */
    public static final String PACKAGE_PREFIX = "org.knifez.fridayboot";
    /**
     * Redis前缀文件夹
     */
    public static final String REDIS_PREFIX_FOLDER = "fridayboot:";

    private AppConstants() {
        throw new IllegalStateException("AppConstants class");
    }
}
