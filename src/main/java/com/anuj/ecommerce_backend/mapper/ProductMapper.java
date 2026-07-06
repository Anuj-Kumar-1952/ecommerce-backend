package com.anuj.ecommerce_backend.mapper;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.anuj.ecommerce_backend.dto.response.*;
import com.anuj.ecommerce_backend.entity.*;

@Component
public class ProductMapper {

        public ProductResponse toResponse(Product product) {

                return ProductResponse.builder()
                                .id(product.getId())
                                .name(product.getName())
                                .slug(product.getSlug())
                                .description(product.getDescription())
                                .categoryId(product.getCategory().getId())
                                .brandId(product.getBrand().getId())
                                .status(product.getStatus())
                                .variants(product.getVariants().stream().map(this::toResponse)
                                                .collect(Collectors.toList()))
                                .build();
        }

        public ProductVariantResponse toResponse(ProductVariant variant) {

                return ProductVariantResponse.builder()
                                .id(variant.getId())
                                .sku(variant.getSku())
                                .price(variant.getPrice())
                                .discountPrice(variant.getDiscountPrice())
                                .stock(variant.getStock())
                                .color(variant.getColor())
                                .storage(variant.getStorage())
                                .images(variant.getImages().stream().map(this::toResponse).collect(Collectors.toList()))
                                .build();
        }

        public ProductImageResponse toResponse(ProductImage image) {

                return ProductImageResponse.builder()
                                .id(image.getId())
                                .imageUrl(image.getImageUrl())
                                .primaryImage(image.getPrimaryImage())
                                .build();
        }
}