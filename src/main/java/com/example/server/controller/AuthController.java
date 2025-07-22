package com.example.server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.server.dto.request.AuthRequest;
import com.example.server.dto.response.AuthResponse;
import com.example.server.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final AuthService authService;

  @PostMapping("/signUp")
  public ResponseEntity<AuthResponse> signUp(@RequestBody AuthRequest request) {
    AuthResponse response = authService.signUp(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PostMapping("/signIn")
  public ResponseEntity<AuthResponse> signIn(@RequestBody AuthRequest request) {
    AuthResponse response = authService.signIn(request);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @PostMapping("/signOut")
  public ResponseEntity<Void> signOut() {
    authService.signOut();
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

}
