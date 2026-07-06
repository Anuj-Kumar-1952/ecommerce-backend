package com.anuj.ecommerce_backend.validator;

import org.springframework.stereotype.Component;

import com.anuj.ecommerce_backend.dto.request.ProductImageRequest;
import com.anuj.ecommerce_backend.dto.request.ProductRequest;
import com.anuj.ecommerce_backend.dto.request.ProductVariantRequest;
import com.anuj.ecommerce_backend.exception.BadRequestException;
import com.anuj.ecommerce_backend.repository.ProductRepository;
import com.anuj.ecommerce_backend.repository.ProductVariantRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductValidator {
    private final ProductRepository productRepository;
    private final ProductVariantRepository productVariantRepository;
   
    public void validateProduct(ProductRequest request) {

        if (productRepository.existsByName(request.getName())) {

            throw new BadRequestException("Product already exists");
        }
    }

    public void validateVariant(ProductVariantRequest request) {

        validatePrimaryImages(request);

        validateDiscountPrice(request);
    }

    public void validatePrimaryImages(ProductVariantRequest request) {

        int primaryCount = 0;

        for (ProductImageRequest image : request.getImages()) {

            if (Boolean.TRUE.equals(image.getPrimaryImage())) {
                primaryCount++;
            }
        }

        if (primaryCount != 1) {

            throw new BadRequestException("Each variant must have exactly one primary image");
        }
    }

    public void validateDiscountPrice(ProductVariantRequest request) {

        if (request.getDiscountPrice() != null
                &&
                request.getDiscountPrice()
                        .compareTo(
                                request.getPrice()) > 0) {

            throw new BadRequestException(
                    "Discount price cannot exceed actual price");
        }
    }

}
