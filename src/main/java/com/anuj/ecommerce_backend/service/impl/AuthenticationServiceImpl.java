package com.anuj.ecommerce_backend.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.anuj.ecommerce_backend.dto.request.LoginRequest;
import com.anuj.ecommerce_backend.dto.request.RegisterRequest;
import com.anuj.ecommerce_backend.dto.response.AuthResponse;
import com.anuj.ecommerce_backend.entity.Role;
import com.anuj.ecommerce_backend.entity.User;
import com.anuj.ecommerce_backend.exception.BadRequestException;
import com.anuj.ecommerce_backend.repository.UserRepository;
import com.anuj.ecommerce_backend.security.service.CustomUserDetails;
import com.anuj.ecommerce_backend.security.service.JwtService;
import com.anuj.ecommerce_backend.service.AuthenticationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

        private final UserRepository userRepository;

        private final PasswordEncoder passwordEncoder;

        private final AuthenticationManager authenticationManager;

        private final JwtService jwtService;

        @Override
        public void register(RegisterRequest request) {

                if (userRepository.existsByEmail(request.getEmail())) {
                        throw new BadRequestException("Email already exists");
                }

                User user = User.builder()
                                .firstName(request.getFirstName())
                                .lastName(request.getLastName())
                                .email(request.getEmail())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .phoneNumber(request.getPhoneNumber())
                                .role(Role.ROLE_CUSTOMER)
                                .build();

                userRepository.save(user);

                log.info("User registered successfully: {}", user.getEmail());
        }

        @Override
        public AuthResponse login(LoginRequest request) {

                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

                User user = userRepository.findByEmail(request.getEmail()).orElseThrow();

                String jwt = jwtService.generateToken(new CustomUserDetails(user));

                log.info("User login successful: {}", user.getEmail());

                return AuthResponse.builder()
                                .token(jwt)
                                .type("Bearer")
                                .email(user.getEmail())
                                .role(user.getRole().name())
                                .build();
        }
}