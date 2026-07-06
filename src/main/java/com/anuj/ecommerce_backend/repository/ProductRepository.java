package com.anuj.ecommerce_backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anuj.ecommerce_backend.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByName(String name);

    boolean existsBySlug(String slug);

    List<Product> findByActiveTrue();

    Optional<Product> findByIdAndActiveTrue(Long id);
}