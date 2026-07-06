package com.anuj.ecommerce_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anuj.ecommerce_backend.entity.Product;

public interface ProductRepository extends JpaRepository<Product,Long>{

    boolean existsBySlug(String slug);
}