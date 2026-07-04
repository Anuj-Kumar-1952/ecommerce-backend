package com.anuj.ecommerce_backend.service;

import com.anuj.ecommerce_backend.dto.request.LoginRequest;
import com.anuj.ecommerce_backend.dto.request.RegisterRequest;
import com.anuj.ecommerce_backend.dto.response.AuthResponse;

public interface AuthenticationService {

    void register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}