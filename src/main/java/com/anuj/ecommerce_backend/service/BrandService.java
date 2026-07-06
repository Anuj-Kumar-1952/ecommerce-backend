package com.anuj.ecommerce_backend.service;

import com.anuj.ecommerce_backend.dto.request.BrandRequest;
import com.anuj.ecommerce_backend.dto.response.BrandResponse;

import java.util.List;

public interface BrandService {

        BrandResponse create(BrandRequest request);

        List<BrandResponse> getAll();

        BrandResponse getById(Long id);

        BrandResponse update(Long id,BrandRequest request);

        void delete(Long id);
}