package com.example.server.expection;

public class UsernameTakenException extends RuntimeException {
  public UsernameTakenException(String message) {
    super(message);
  }
}
