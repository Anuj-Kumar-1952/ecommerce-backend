package com.anuj.ecommerce_backend.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anuj.ecommerce_backend.dto.request.CategoryRequest;
import com.anuj.ecommerce_backend.dto.response.CategoryResponse;
import com.anuj.ecommerce_backend.entity.Category;
import com.anuj.ecommerce_backend.exception.BadRequestException;
import com.anuj.ecommerce_backend.exception.ResourceNotFoundException;
import com.anuj.ecommerce_backend.mapper.CategoryMapper;
import com.anuj.ecommerce_backend.repository.CategoryRepository;
import com.anuj.ecommerce_backend.service.CategoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
        private final CategoryRepository categoryRepository;

        private final CategoryMapper categoryMapper;

        @Transactional
        @Override
        public CategoryResponse create(CategoryRequest request) {

                log.info("Creating category {}", request.getName());

                if (categoryRepository.existsByName(request.getName())) {

                        throw new BadRequestException("Category already exists");
                }

                Category parentCategory = null;

                if (request.getParentCategoryId() != null) {

                        parentCategory = categoryRepository.findByIdAndActiveTrue(request.getParentCategoryId())
                                        .orElseThrow(() -> new ResourceNotFoundException("Parent category not found"));
                }

                Category category = Category.builder()
                                .name(request.getName())
                                .description(request.getDescription())
                                .parentCategory(parentCategory)
                                .build();

                Category savedCategory = categoryRepository.save(category);

                log.info("Category created {}", savedCategory.getId());

                return categoryMapper.toResponse(savedCategory);
        }

        @Override
        public List<CategoryResponse> getAll() {

                log.info("Fetching all categories");

                return categoryRepository
                                .findByActiveTrue()
                                .stream()
                                .map(categoryMapper::toResponse)
                                .toList();
        }

        @Override
        public CategoryResponse getById(Long id) {

                log.info("Fetching category {}", id);

                Category category = categoryRepository
                                .findByIdAndActiveTrue(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

                return categoryMapper.toResponse(category);
        }

        @Transactional
        @Override
        public CategoryResponse update(Long id, CategoryRequest request) {

                Category category = categoryRepository
                                .findByIdAndActiveTrue(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

                category.setName(request.getName());

                category.setDescription(request.getDescription());

                if (request.getParentCategoryId() != null) {

                        Category parent = categoryRepository
                                        .findByIdAndActiveTrue(request.getParentCategoryId())
                                        .orElseThrow(() -> new ResourceNotFoundException("Parent category not found"));

                        category.setParentCategory(parent);
                } else
                        category.setParentCategory(null);

                return categoryMapper.toResponse(categoryRepository.save(category));
        }

        @Transactional
        @Override
        public void delete(Long id) {

                Category category = categoryRepository
                                .findByIdAndActiveTrue(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

                category.setActive(false);

                categoryRepository.save(category);

                log.info("Category deleted {}", id);
        }
}