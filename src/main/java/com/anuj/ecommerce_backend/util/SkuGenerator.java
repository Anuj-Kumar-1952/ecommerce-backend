package com.anuj.ecommerce_backend.util;

import org.springframework.stereotype.Component;

@Component
public class SkuGenerator {

        public String generate(String brand, String product, String storage, String color) {

                String brandCode = brand.length() >= 3 ? brand.substring(0, 3) : brand;

                String productCode = product.length() >= 4 ? product.substring(0, 4) : product;

                String colorCode = color.length() >= 3 ? color.substring(0, 3) : color;

                return String.format("%s-%s-%s-%s", brandCode.toUpperCase(), productCode.toUpperCase(),
                                storage.toUpperCase(), colorCode.toUpperCase()).replace(" ", "");
        }
}