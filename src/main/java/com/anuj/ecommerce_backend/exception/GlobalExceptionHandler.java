package com.anuj.ecommerce_backend.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.anuj.ecommerce_backend.dto.response.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(BadRequestException.class)
        public ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException ex) {

                log.warn("Bad request: {}", ex.getMessage());

                ErrorResponse response = ErrorResponse.builder()
                                .success(false)
                                .status(HttpStatus.BAD_REQUEST.value())
                                .message(ex.getMessage())
                                .timestamp(LocalDateTime.now())
                                .build();

                return ResponseEntity.badRequest().body(response);
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {

                Map<String, String> errors = new HashMap<>();

                for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                        errors.put(error.getField(), error.getDefaultMessage());
                }

                log.warn("Validation failed: {}", errors);

                ErrorResponse response = ErrorResponse.builder()
                                .success(false)
                                .status(HttpStatus.BAD_REQUEST.value())
                                .message("Validation failed")
                                .errors(errors)
                                .timestamp(LocalDateTime.now())
                                .build();

                return ResponseEntity.badRequest().body(response);
        }

        @ExceptionHandler(BadCredentialsException.class)
        public ResponseEntity<ErrorResponse> handleBadCredentials(
                        BadCredentialsException ex) {

                log.warn("Authentication failed");

                ErrorResponse response = ErrorResponse.builder()
                                .success(false)
                                .status(HttpStatus.UNAUTHORIZED.value())
                                .message("Invalid credentials")
                                .timestamp(LocalDateTime.now())
                                .build();

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        @ExceptionHandler(UnauthorizedException.class)
        public ResponseEntity<ErrorResponse> handleUnauthorized(
                        UnauthorizedException ex) {

                log.warn("Unauthorized access: {}", ex.getMessage());

                ErrorResponse response = ErrorResponse.builder()
                                .success(false)
                                .status(HttpStatus.UNAUTHORIZED.value())
                                .message(ex.getMessage())
                                .timestamp(LocalDateTime.now())
                                .build();

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponse> handleException(
                        Exception ex) {

                log.error("Internal server error",ex);

                ErrorResponse response = ErrorResponse.builder()
                                .success(false)
                                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .message("Internal server error")
                                .timestamp(LocalDateTime.now())
                                .build();

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
}