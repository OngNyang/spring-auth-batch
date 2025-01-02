package org.example.springauthbatch.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RedisSessionService {
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisSessionService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Set<String>  getActiveSessions() {
//        return (redisTemplate.keys("spring:session:sessions:*"));
        return (redisTemplate.keys("*"));
    }

    public Object   getSessionDetails(String seesionId) {
        return (redisTemplate.opsForHash().entries(seesionId));
    }
}
