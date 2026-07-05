package com.anuj.ecommerce_backend.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anuj.ecommerce_backend.dto.request.LoginRequest;
import com.anuj.ecommerce_backend.dto.request.RegisterRequest;
import com.anuj.ecommerce_backend.dto.response.ApiResponse;
import com.anuj.ecommerce_backend.dto.response.AuthResponse;
import com.anuj.ecommerce_backend.service.AuthenticationService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
        private final AuthenticationService authenticationService;

        @Operation(summary = "Register a new user")
        @PostMapping("/register")
        public ApiResponse<String> register(@Valid @RequestBody RegisterRequest request) {

                authenticationService.register(request);

                return ApiResponse.<String>builder()
                                .success(true)
                                .message("User registered successfully")
                                .data(request.getEmail())
                                .build();
        }

        @Operation(summary = "Authenticate user and generate JWT")
        @PostMapping("/login")
        public ApiResponse<AuthResponse> login(@Valid @RequestBody LoginRequest request) {

                return ApiResponse.<AuthResponse>builder().success(true)
                                .message("Login successful")
                                .data(authenticationService.login(request))
                                .build();
        }
}