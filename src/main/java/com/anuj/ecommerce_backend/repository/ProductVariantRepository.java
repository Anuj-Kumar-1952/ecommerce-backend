package com.anuj.ecommerce_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anuj.ecommerce_backend.entity.ProductVariant;

public interface ProductVariantRepository extends JpaRepository<ProductVariant,Long>{

    boolean existsBySku(String sku);
}