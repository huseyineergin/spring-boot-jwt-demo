package com.example.server.expection;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExpectionHandler {

  @ExceptionHandler(TokenInvalidException.class)
  public ResponseEntity<ErrorResponse> handleTokenInvalidException(TokenInvalidException ex) {
    ErrorResponse error = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
    return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(UsernameTakenException.class)
  public ResponseEntity<ErrorResponse> handleUsernameTakenException(UsernameTakenException ex) {
    ErrorResponse error = new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
    return new ResponseEntity<>(error, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex) {
    ErrorResponse error = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
    return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
  }

}
