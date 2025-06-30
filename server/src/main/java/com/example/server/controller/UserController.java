package com.example.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

  @GetMapping("/me")
  public ResponseEntity<String> getLoggedInUsername(Authentication authentication) {
    String username = authentication.getName();
    return ResponseEntity.ok(username);
  }

}
