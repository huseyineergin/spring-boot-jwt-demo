package com.example.server.expection;

public class TokenInvalidException extends RuntimeException {
  public TokenInvalidException(String message) {
    super(message);
  }
}
