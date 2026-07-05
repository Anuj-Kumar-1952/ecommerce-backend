package com.anuj.ecommerce_backend.security.service;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

        @Value("${jwt.secret}")
        private String secret;

        @Value("${jwt.expiration}")
        private Long expiration;

        // Generate secret key
        private SecretKey getKey() {
                return Keys.hmacShaKeyFor(secret.getBytes());
        }

        // Generate JWT token
        public String generateToken(UserDetails userDetails) {

                return Jwts.builder()
                                .subject(userDetails.getUsername())
                                .issuedAt(new Date())
                                .expiration(new Date(System.currentTimeMillis() + expiration))
                                .signWith(getKey())
                                .compact();
        }

        // Extract username from token
        public String extractUsername(String token) {
                return extractClaim(token, Claims::getSubject);
        }

        // Extract any claim
        public <T> T extractClaim(String token, Function<Claims, T> resolver) {

                Claims claims = Jwts.parser()
                                .verifyWith(getKey())
                                .build()
                                .parseSignedClaims(token)
                                .getPayload();

                return resolver.apply(claims);
        }

        // Check token validity
        public boolean isTokenValid(String token, UserDetails userDetails) {

                return extractUsername(token).equals(userDetails.getUsername())
                                && !extractClaim(token, Claims::getExpiration).before(new Date());
        }
}