package com.anuj.ecommerce_backend.repository;

import com.anuj.ecommerce_backend.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    boolean existsByName(String name);
}