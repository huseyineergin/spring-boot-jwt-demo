package com.example.server.service;

import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserTokenService {

  private final RedisTemplate<String, String> redisTemplate;
  private static final String JWT_TOKEN_PREFIX = "JWT_TOKEN:";
  private static final String USER_TOKEN_PREFIX = "USER_TOKEN:";

  public void saveUserToken(String username, String token, Duration ttl) {
    String oldToken = redisTemplate.opsForValue().get(USER_TOKEN_PREFIX + username);

    if (oldToken != null) {
      revokeToken(oldToken);
    }

    redisTemplate.opsForValue().set(USER_TOKEN_PREFIX + username, token, ttl);
    saveToken(token, ttl);
  }

  public void revokeTokenForUser(String username) {
    String token = redisTemplate.opsForValue().get(USER_TOKEN_PREFIX + username);

    if (token != null) {
      revokeToken(token);
      redisTemplate.delete(USER_TOKEN_PREFIX + username);
    }
  }

  public void saveToken(String token, Duration ttl) {
    redisTemplate.opsForValue().set(JWT_TOKEN_PREFIX + token, "ACTIVE", ttl);
  }

  public void revokeToken(String token) {
    redisTemplate.opsForValue().set(JWT_TOKEN_PREFIX + token, "PASSIVE");
  }

  public boolean isTokenValid(String token) {
    String key = JWT_TOKEN_PREFIX + token;
    Long ttl = redisTemplate.getExpire(key);
    String status = redisTemplate.opsForValue().get(key);
    return "ACTIVE".equals(status) && (ttl == null || ttl > 0);
  }

}
