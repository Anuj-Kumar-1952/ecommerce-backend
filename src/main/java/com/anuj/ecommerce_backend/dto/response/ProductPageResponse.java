package com.anuj.ecommerce_backend.dto.response;

import java.util.List;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductPageResponse {

    private List<ProductResponse> content;

    private Integer page;

    private Integer size;

    private Long totalElements;

    private Integer totalPages;

    private Boolean last;
}