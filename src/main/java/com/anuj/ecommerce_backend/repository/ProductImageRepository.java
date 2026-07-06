package com.anuj.ecommerce_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anuj.ecommerce_backend.entity.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage,Long>{
        
}