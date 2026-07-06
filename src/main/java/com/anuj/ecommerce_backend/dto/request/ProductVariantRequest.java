package com.anuj.ecommerce_backend.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariantRequest {

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal price;

    private BigDecimal discountPrice;

    @NotNull
    @Min(0)
    private Integer stock;

    @NotBlank
    private String color;

    @NotBlank
    private String storage;

    @Valid
    @NotEmpty
    private List<ProductImageRequest> images;
}