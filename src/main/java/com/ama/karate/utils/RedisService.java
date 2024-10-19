package com.ama.karate.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.ama.karate.dto.SessionDto;

@Configuration
public class RedisService {

    private RedisTemplate<String, Object> redisTemplate;

    public void setSession(String sessionKey, SessionDto sessionObj){
        ValueOperations<String, Object> valueOps = redisTemplate.opsForValue();
        valueOps.set(sessionKey, sessionObj);
    }

    public SessionDto getSession(String sessionKey) {
        ValueOperations<String, Object> valueOps = redisTemplate.opsForValue();
        return (SessionDto) valueOps.get(sessionKey);
    }
    
    public void deleteSession(String sessionKey) {
        redisTemplate.delete(sessionKey);
    }
}
