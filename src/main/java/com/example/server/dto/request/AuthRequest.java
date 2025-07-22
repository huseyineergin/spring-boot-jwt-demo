package com.example.server.dto.request;

import lombok.Data;

@Data
public class AuthRequest {
  private String username;
  private String password;
}
