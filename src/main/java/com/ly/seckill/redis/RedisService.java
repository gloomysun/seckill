package com.ly.seckill.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
    @Autowired
    private RedisTemplate redisTemplate;

    public void set(String prefix, String key, Object value, int time) {
        redisTemplate.opsForValue().set(prefix + ":" + key, value);
        if (time > 0) {
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
        }
    }

    public Object get(String prefix, String key) {
        return redisTemplate.opsForValue().get(prefix + ":" + key);
    }

    public void set(String key, Object value, int time) {
        redisTemplate.opsForValue().set(key, value);
        if (time > 0) {
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
        }
    }

    public void set(String key, Object value) {
        this.set(key, value, -1);
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public long decr(String prefix, String key) {
        return redisTemplate.opsForValue().increment(prefix + ":" + key, -1);
    }
}
