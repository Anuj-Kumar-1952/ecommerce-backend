package com.anuj.ecommerce_backend.dto.response;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {

    private boolean success;

    private int status;

    private String message;

    private Map<String,String> errors;

    private LocalDateTime timestamp;
}
