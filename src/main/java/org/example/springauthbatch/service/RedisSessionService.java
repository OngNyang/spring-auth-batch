package org.example.springauthbatch.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.springauthbatch.Exception.UtilProcessException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
public class RedisSessionService {
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisSessionService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Set<String>  getActiveSessions() {
//        return (redisTemplate.keys("spring:session:sessions:*"));
        return (redisTemplate.keys("spring:session:sessions:*"));
    }

//    public Object   getSessionDetails(String seesionId) {
//        return (redisTemplate.opsForValue().get(seesionId));
//    }

    public Object getSessionDetails(String sessionId) {
        // Redis에서 키의 데이터 타입 확인
        String type = redisTemplate.type(sessionId).code();
        System.out.print("type: ");
        System.out.println(type);

        if ("hash".equals(type)) {
            return redisTemplate.opsForHash().entries(sessionId);
        } else if ("string".equals(type)) {
            return (convertSessionDetailsToMap(sessionId));
        } else {
            throw new IllegalStateException("Unsupported Redis data type: " + type);
        }
    }

    public Object   convertSessionDetailsToMap(String sessionId) {
        String          sessionDetails = (String) redisTemplate.opsForValue().get(sessionId);
        ObjectMapper    objectMapper = new ObjectMapper();

        try {
            return (objectMapper.readValue(sessionDetails, Map.class));
        } catch (Exception e) {
            throw new UtilProcessException("Failed to parse session details for sessionId '" + sessionId + "'", e, 1);
        }
    }

}
