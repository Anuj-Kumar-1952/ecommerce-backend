package com.anuj.ecommerce_backend.dto.response;

import com.anuj.ecommerce_backend.enums.ProductStatus;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    private Long id;

    private String name;

    private String slug;

    private String description;

    private Long categoryId;

    private Long brandId;

    private ProductStatus status;

    private List<ProductVariantResponse> variants;
}