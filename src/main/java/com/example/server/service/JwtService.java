package com.example.server.service;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {

  @Value("${jwt.secret}")
  private String SECRET;

  @Value("${jwt.expiration}")
  private long EXPIRATION;

  private SecretKey getSignKey() {
    return Keys.hmacShaKeyFor(SECRET.getBytes());
  }

  public String generateToken(String username) {
    return Jwts.builder()
        .subject(username)
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
        .signWith(getSignKey(), Jwts.SIG.HS256)
        .compact();
  }

  public boolean validateToken(String token, String userDetails) {
    final String username = extractUsername(token);
    return username.equals(userDetails) && !isTokenExpired(token);
  }

  public String extractUsername(String token) {
    return Jwts.parser()
        .verifyWith(getSignKey())
        .build()
        .parseSignedClaims(token)
        .getPayload()
        .getSubject();
  }

  public long getExpiration() {
    return EXPIRATION;
  }

  private boolean isTokenExpired(String token) {
    return Jwts
        .parser()
        .verifyWith(getSignKey())
        .build()
        .parseSignedClaims(token)
        .getPayload()
        .getExpiration()
        .before(new Date());
  }
}
