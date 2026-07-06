package com.anuj.ecommerce_backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anuj.ecommerce_backend.dto.request.CategoryRequest;
import com.anuj.ecommerce_backend.dto.response.ApiResponse;
import com.anuj.ecommerce_backend.dto.response.CategoryResponse;
import com.anuj.ecommerce_backend.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "Create category")
    @PostMapping
    public ApiResponse<CategoryResponse> create(@Valid @RequestBody CategoryRequest request) {

        return ApiResponse.<CategoryResponse>builder()
                .success(true)
                .message("Category created successfully")
                .data(categoryService.create(request))
                .build();
    }

    @Operation(summary = "Get all categories")
    @GetMapping
    public ApiResponse<List<CategoryResponse>> getAll() {

        return ApiResponse.<List<CategoryResponse>>builder()
                .success(true)
                .message("Categories fetched successfully")
                .data(categoryService.getAll())
                .build();
    }

    @Operation(summary = "Get category by id")
    @GetMapping("/{id}")
    public ApiResponse<CategoryResponse> getById(@PathVariable Long id) {

        return ApiResponse.<CategoryResponse>builder()
                .success(true)
                .message("Category fetched successfully")
                .data(categoryService.getById(id))
                .build();
    }

    @Operation(summary = "Update category")
    @PutMapping("/{id}")
    public ApiResponse<CategoryResponse> update(@PathVariable Long id,@Valid @RequestBody CategoryRequest request) {

        return ApiResponse.<CategoryResponse>builder()
                .success(true)
                .message("Category updated successfully")
                .data(categoryService.update(id,request))
                .build();
    }

    @Operation(summary = "Delete category")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {

        categoryService.delete(id);

        return ApiResponse.<Void>builder()
                .success(true)
                .message("Category deleted successfully")
                .build();
    }
}