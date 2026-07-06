package com.anuj.ecommerce_backend.service;

import java.util.List;

import com.anuj.ecommerce_backend.dto.request.ProductRequest;
import com.anuj.ecommerce_backend.dto.response.ProductResponse;

public interface ProductService {

        ProductResponse create(ProductRequest request);

        List<ProductResponse> getAll();

        ProductResponse getById(Long id);

        void delete(Long id);
}