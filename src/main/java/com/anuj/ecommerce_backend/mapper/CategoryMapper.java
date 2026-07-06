package com.anuj.ecommerce_backend.mapper;

import com.anuj.ecommerce_backend.dto.response.CategoryResponse;
import com.anuj.ecommerce_backend.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

        public CategoryResponse toResponse(Category category) {

                return CategoryResponse
                                .builder()
                                .id(category.getId())
                                .name(category.getName())
                                .description(category.getDescription())
                                .active(category.getActive())
                                .parentCategoryId(category.getParentCategory() != null
                                                ? category.getParentCategory().getId()
                                                : null)
                                .build();
        }
}