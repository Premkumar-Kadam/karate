package com.ama.karate.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.ama.karate.dto.SessionDto;

@Configuration
public class RedisService {

    private RedisTemplate<String, Object> redisTemplate;

    public boolean setSession(String sessionKey, SessionDto sessionObj){
        try {
            ValueOperations<String, Object> valueOps = redisTemplate.opsForValue();
            valueOps.set(sessionKey, sessionObj);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public SessionDto getSession(String sessionKey) {
        ValueOperations<String, Object> valueOps = redisTemplate.opsForValue();
        return (SessionDto) valueOps.get(sessionKey);
    }
    
    public void deleteSession(String sessionKey) {
        redisTemplate.delete(sessionKey);
    }
}
