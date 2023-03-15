package org.knifez.fridaybootadmin.utils;

import org.knifez.fridaybootcore.constants.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {
    @Autowired
    private final StringRedisTemplate redisTemplate;

    public RedisUtils(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(AppConstants.REDIS_PREFIX_FOLDER + key);
    }

    public String get(String name) {
        return redisTemplate.opsForValue().get(AppConstants.REDIS_PREFIX_FOLDER + name);
    }

    public void set(String key, String value) {
        redisTemplate.opsForValue().set(AppConstants.REDIS_PREFIX_FOLDER + key, value);
    }

    public void set(String key, String value, long offset) {
        redisTemplate.opsForValue().set(AppConstants.REDIS_PREFIX_FOLDER + key, value, offset);
    }

    public void set(String key, String value, long offset, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(AppConstants.REDIS_PREFIX_FOLDER + key, value, offset, timeUnit);
    }

    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

}
