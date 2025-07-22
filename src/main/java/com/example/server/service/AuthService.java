package com.example.server.service;

import java.time.Duration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.example.server.dto.request.AuthRequest;
import com.example.server.dto.response.AuthResponse;
import com.example.server.entity.User;
import com.example.server.expection.UsernameTakenException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final JwtService jwtService;
  private final UserService userService;
  private final UserTokenService userTokenService;
  private final HttpServletRequest httpServletRequest;
  private final AuthenticationManager authenticationManager;

  public AuthResponse signUp(AuthRequest request) {
    if (userService.isUsernameExists(request.getUsername())) {
      throw new UsernameTakenException("Username is already taken.");
    }

    User user = userService.createUser(request);

    String token = jwtService.generateToken(user.getUsername());
    Duration ttl = Duration.ofMillis(jwtService.getExpiration());
    userTokenService.saveUserToken(user.getUsername(), token, ttl);

    return AuthResponse.builder().token(token).build();
  }

  public AuthResponse signIn(AuthRequest request) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()) //
      );
    } catch (AuthenticationException ex) {
      throw new BadCredentialsException("Invalid username or password.");
    }

    String token = jwtService.generateToken(request.getUsername());
    Duration ttl = Duration.ofMillis(jwtService.getExpiration());
    userTokenService.saveUserToken(request.getUsername(), token, ttl);

    return AuthResponse.builder().token(token).build();
  }

  public void signOut() {
    String token = httpServletRequest.getHeader("Authorization").substring(7);
    String username = jwtService.extractUsername(token);
    userTokenService.revokeTokenForUser(username);
    userTokenService.revokeToken(token);
  }

}
