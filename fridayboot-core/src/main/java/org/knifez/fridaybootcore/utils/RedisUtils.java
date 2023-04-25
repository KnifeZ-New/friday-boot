package org.knifez.fridaybootcore.utils;

import lombok.extern.slf4j.Slf4j;
import org.knifez.fridaybootcore.constants.AppConstants;

@Slf4j
public class RedisUtils {
    public static String formatKey(String key) {
        return AppConstants.REDIS_PREFIX_FOLDER + key;
    }
}
