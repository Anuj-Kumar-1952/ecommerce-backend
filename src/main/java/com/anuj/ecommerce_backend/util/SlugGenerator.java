package com.anuj.ecommerce_backend.util;

import org.springframework.stereotype.Component;

@Component
public class SlugGenerator {

    public String generate(String value) {

        return value
                .trim()
                .toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-");
    }
}