package com.anuj.ecommerce_backend.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    @NotBlank
    @Size(max = 200)
    private String name;

    @Size(max = 3000)
    private String description;

    @NotNull
    private Long categoryId;

    @NotNull
    private Long brandId;

    @Valid
    @NotEmpty
    private List<ProductVariantRequest> variants;
}