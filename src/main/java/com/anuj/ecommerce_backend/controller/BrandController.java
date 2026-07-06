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

import com.anuj.ecommerce_backend.dto.request.BrandRequest;
import com.anuj.ecommerce_backend.dto.response.ApiResponse;
import com.anuj.ecommerce_backend.dto.response.BrandResponse;
import com.anuj.ecommerce_backend.service.BrandService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @Operation(summary = "Create brand")
    @PostMapping
    public ApiResponse<BrandResponse> create(@Valid @RequestBody BrandRequest request) {

        return ApiResponse
                .<BrandResponse>builder()
                .success(true)
                .message("Brand created successfully")
                .data(brandService.create(request))
                .build();
    }

    @Operation(summary = "Get all brands")
    @GetMapping
    public ApiResponse<List<BrandResponse>> getAll() {

        return ApiResponse.<List<BrandResponse>>builder()
                .success(true)
                .message("Brands fetched successfully")
                .data(brandService.getAll())
                .build();
    }

    @Operation(summary = "Get brand by id")
    @GetMapping("/{id}")
    public ApiResponse<BrandResponse> getById(@PathVariable Long id) {

        return ApiResponse.<BrandResponse>builder()
                .success(true)
                .message("Brand fetched successfully")
                .data(brandService.getById(id))
                .build();
    }

    @Operation(summary = "Update brand")
    @PutMapping("/{id}")
    public ApiResponse<BrandResponse> update(@PathVariable Long id,@Valid @RequestBody BrandRequest request) {

        return ApiResponse
                .<BrandResponse>builder()
                .success(true)
                .message("Brand updated successfully")
                .data(brandService.update(id,request))
                .build();
    }

    @Operation(summary = "Delete brand")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {

        brandService.delete(id);

        return ApiResponse.<Void>builder()
                .success(true)
                .message("Brand deleted successfully")
                .build();
    }
}