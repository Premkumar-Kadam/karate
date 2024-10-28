package com.ama.karate.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public boolean setSession(String sessionKey, String value) {
        try {
            redisTemplate.opsForValue().set(sessionKey, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getSession(String sessionKey) {
        try {
            return redisTemplate.opsForValue().get(sessionKey);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteSession(String sessionKey) {
        try {
            return redisTemplate.delete(sessionKey) != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
