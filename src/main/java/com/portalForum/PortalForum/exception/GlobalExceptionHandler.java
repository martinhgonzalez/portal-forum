package com.portalForum.PortalForum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  public static ResponseEntity<?> notFoundException(NotFoundException ex) {

    Map<String, String> error = new HashMap<>();
    String fieldName = "errorDescription";
    String errorMessage = ex.getMessage();
    error.put(fieldName, errorMessage);

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(PortalForumException.class)
  public static ResponseEntity<?> portalForumException(PortalForumException ex) {

    Map<String, String> error = new HashMap<>();
    String fieldName = "errorDescription";
    String errorMessage = ex.getMessage();
    error.put(fieldName, errorMessage);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public static ResponseEntity<?> methodArgumentNotValidException(
      MethodArgumentNotValidException ex) {

    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult()
        .getAllErrors()
        .forEach(
            (error) -> {
              String fieldName = ((FieldError) error).getField();
              String errorMessage = error.getDefaultMessage();
              errors.put(fieldName, errorMessage);
            });

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
  }

  @ExceptionHandler(BadCredentialsException.class)
  public static ResponseEntity<?> badCredentialsException(BadCredentialsException ex) {

    Map<String, String> error = new HashMap<>();

    String fieldName = "errorDescription";
    String errorMessage = ex.getMessage();
    error.put(fieldName, errorMessage);

    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  public static ResponseEntity<?> usernameNotFoundException(UsernameNotFoundException ex) {

    Map<String, String> error = new HashMap<>();

    String fieldName = "errorDescription";
    String errorMessage = ex.getMessage();
    error.put(fieldName, errorMessage);

    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
  }
}
