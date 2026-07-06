package com.anuj.ecommerce_backend.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrandResponse {

    private Long id;

    private String name;

    private String description;

    private String logoUrl;

    private Boolean active;
}