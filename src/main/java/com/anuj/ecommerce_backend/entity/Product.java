package com.anuj.ecommerce_backend.entity;

import java.util.ArrayList;
import java.util.List;

import com.anuj.ecommerce_backend.enums.ProductStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "products")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity {

        @Column(nullable = false, length = 200)
        private String name;

        @Column(nullable = false, unique = true)
        private String slug;

        @Column(length = 3000)
        private String description;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "category_id")
        private Category category;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "brand_id")
        private Brand brand;

        @Builder.Default
        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private ProductStatus status = ProductStatus.ACTIVE;

        @Builder.Default
        @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
        private List<ProductVariant> variants = new ArrayList<>();

        @Override
        public boolean equals(Object o) {
                if (this == o)
                        return true;

                if (!(o instanceof Product product))
                        return false;

                return getId() != null && getId().equals(product.getId());
        }

        @Override
        public int hashCode() {
                return getClass().hashCode();
        }

        public void addVariant(ProductVariant variant) {

                variants.add(variant);
                variant.setProduct(this);
        }

        public void removeVariant(ProductVariant variant) {

                variants.remove(variant);
                variant.setProduct(null);
        }
}