package com.anuj.ecommerce_backend.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categories")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category extends BaseEntity {

        @Column(nullable = false, unique = true, length = 100)
        private String name;

        @Column(length = 500)
        private String description;

        @Column(nullable = false)
        @Builder.Default
        private Boolean active = true;

        // Parent Category
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "parent_category_id")
        private Category parentCategory;

        // Child Categories
        @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
        @Builder.Default
        private List<Category> subCategories = new ArrayList<>();

        @Override
        public boolean equals(Object o) {

                if (this == o)
                        return true;

                if (!(o instanceof Category category))
                        return false;

                return getId() != null && getId().equals(category.getId());
        }

        @Override
        public int hashCode() {
                return getClass().hashCode();
        }

        public void addSubCategory(Category category) {
                subCategories.add(category);
                category.setParentCategory(this);
        }

        public void removeSubCategory(Category category) {
                subCategories.remove(category);
                category.setParentCategory(null);
        }
}
