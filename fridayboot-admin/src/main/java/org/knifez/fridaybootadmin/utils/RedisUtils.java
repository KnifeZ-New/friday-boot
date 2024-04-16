package org.knifez.fridaybootadmin.utils;

import lombok.extern.slf4j.Slf4j;
import org.knifez.fridaybootcore.common.constants.AppConstants;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class RedisUtils {
    public static String formatKey(String key) {
        return AppConstants.REDIS_PREFIX_FOLDER + key;
    }

    public static String getStr(StringRedisTemplate redisTemplate, String key) {
        return redisTemplate.opsForValue().get(formatKey(key));
    }

    public static void setStr(StringRedisTemplate redisTemplate, String key, String value, Long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(formatKey(key), value, timeout, timeUnit);
    }

    public static void setUserToken(StringRedisTemplate redisTemplate, String key, String value, Boolean isAdd) {
        var tokenList = getUserToken(redisTemplate, key);
        List<String> tokens = new ArrayList<>(tokenList);
        if (isAdd) {
            if (tokens.stream().noneMatch(x -> x.equals(value))) {
                tokens.add(value);
            }
        } else {
            tokens.remove(value);
        }
        redisTemplate.opsForValue().set(formatKey(key), String.join(",", tokens));
    }

    public static List<String> getUserToken(StringRedisTemplate redisTemplate, String key) {
        var redisVal = redisTemplate.opsForValue().get(formatKey(key));
        if (redisVal != null) {
            return List.of(redisVal.split(","));
        }
        return new ArrayList<>();
    }
}
