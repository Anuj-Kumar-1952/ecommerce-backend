package com.anuj.ecommerce_backend.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariantResponse {

    private Long id;

    private String sku;

    private BigDecimal price;

    private BigDecimal discountPrice;

    private Integer stock;

    private String color;

    private String storage;

    private List<ProductImageResponse> images;
}