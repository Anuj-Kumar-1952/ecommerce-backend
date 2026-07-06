package com.anuj.ecommerce_backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anuj.ecommerce_backend.entity.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    boolean existsByName(String name);

    List<Brand> findByActiveTrue();

    Optional<Brand> findByIdAndActiveTrue(Long id);
}