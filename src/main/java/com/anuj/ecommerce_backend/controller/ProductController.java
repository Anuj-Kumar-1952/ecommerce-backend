package com.anuj.ecommerce_backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anuj.ecommerce_backend.dto.request.ProductRequest;
import com.anuj.ecommerce_backend.dto.response.ApiResponse;
import com.anuj.ecommerce_backend.dto.response.ProductPageResponse;
import com.anuj.ecommerce_backend.dto.response.ProductResponse;
import com.anuj.ecommerce_backend.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Create product")
    @PostMapping
    public ApiResponse<ProductResponse> create(@Valid @RequestBody ProductRequest request) {

        return ApiResponse.<ProductResponse>builder()
                .success(true)
                .message("Product created successfully")
                .data(productService.create(request))
                .build();
    }

    @Operation(summary = "Get all products")
    @GetMapping
    public ApiResponse<List<ProductResponse>> getAll() {

        return ApiResponse.<List<ProductResponse>>builder()
                .success(true)
                .message("Products fetched successfully")
                .data(productService.getAll())
                .build();
    }

    @Operation(summary = "Get product by id")
    @GetMapping("/{id}")
    public ApiResponse<ProductResponse> getById(@PathVariable Long id) {

        return ApiResponse.<ProductResponse>builder()
                .success(true)
                .message("Product fetched successfully")
                .data(productService.getById(id))
                .build();
    }

    @Operation(summary = "Delete product")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {

        productService.delete(id);

        return ApiResponse.<Void>builder()
                .success(true)
                .message("Product deleted successfully")
                .build();
    }

    @Operation(summary = "Search products")
    @GetMapping("/search")
    public ApiResponse<ProductPageResponse> search(

            @RequestParam(required = false) String keyword,

            @RequestParam(defaultValue = "0") Integer page,

            @RequestParam(defaultValue = "10") Integer size,

            @RequestParam(defaultValue = "name") String sortBy,

            @RequestParam(defaultValue = "asc") String direction) {

        return ApiResponse.<ProductPageResponse>builder()
                .success(true)
                .message("Products fetched successfully")
                .data(productService.search(keyword, page, size, sortBy, direction))
                .build();
    }
}