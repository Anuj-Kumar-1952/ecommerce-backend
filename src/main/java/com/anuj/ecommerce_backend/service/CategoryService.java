package com.anuj.ecommerce_backend.service;

import com.anuj.ecommerce_backend.dto.request.CategoryRequest;
import com.anuj.ecommerce_backend.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {

        CategoryResponse create(CategoryRequest request);

        List<CategoryResponse> getAll();

        CategoryResponse getById(Long id);

        CategoryResponse update(Long id, CategoryRequest request);

        void delete(Long id);
}