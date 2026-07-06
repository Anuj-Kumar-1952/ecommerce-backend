package com.anuj.ecommerce_backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.anuj.ecommerce_backend.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByName(String name);

    boolean existsBySlug(String slug);

    List<Product> findByActiveTrue();

    Page<Product> findByActiveTrue(Pageable pageable);

    Page<Product> findByNameContainingIgnoreCaseAndActiveTrue(String keyword, Pageable pageable);

    Page<Product> findByCategoryIdAndActiveTrue(Long categoryId, Pageable pageable);

    Page<Product> findByBrandIdAndActiveTrue(Long brandId, Pageable pageable);

    Optional<Product> findByIdAndActiveTrue(Long id);
}