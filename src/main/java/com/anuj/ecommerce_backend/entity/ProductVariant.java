package com.anuj.ecommerce_backend.entity;

import java.math.BigDecimal;
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
@Table(name = "product_variants")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariant extends BaseEntity {

        @Column(nullable = false, unique = true, length = 100)
        private String sku;

        @Column(nullable = false, precision = 10, scale = 2)
        private BigDecimal price;

        @Column(precision = 10, scale = 2)
        private BigDecimal discountPrice;

        @Column(nullable = false)
        private Integer stock;

        private String color;

        private String storage;

        @Builder.Default
        @Column(nullable = false)
        private Boolean active = true;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "product_id")
        private Product product;

        @Builder.Default
        @OneToMany(mappedBy = "variant", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
        private List<ProductImage> images = new ArrayList<>();

        @Override
        public boolean equals(Object o) {

                if (this == o)
                        return true;

                if (!(o instanceof ProductVariant variant))
                        return false;

                return getId() != null && getId().equals(variant.getId());
        }

        @Override
        public int hashCode() {
                return getClass().hashCode();
        }

        public void addImage(ProductImage image) {
                images.add(image);
                image.setVariant(this);
        }

        public void removeImage(ProductImage image) {
                images.remove(image);
                image.setVariant(null);
        }

}