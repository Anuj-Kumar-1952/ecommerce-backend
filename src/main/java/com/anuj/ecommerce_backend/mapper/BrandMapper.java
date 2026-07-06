package com.anuj.ecommerce_backend.mapper;

import com.anuj.ecommerce_backend.dto.response.BrandResponse;
import com.anuj.ecommerce_backend.entity.Brand;
import org.springframework.stereotype.Component;

@Component
public class BrandMapper {

        public BrandResponse toResponse(Brand brand) {

                return BrandResponse.builder()
                                .id(brand.getId())
                                .name(brand.getName())
                                .description(brand.getDescription())
                                .logoUrl(brand.getLogoUrl())
                                .active(brand.getActive())
                                .build();
        }
}