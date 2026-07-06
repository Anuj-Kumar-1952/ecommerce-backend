package com.anuj.ecommerce_backend.repository;

import com.anuj.ecommerce_backend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);
}
