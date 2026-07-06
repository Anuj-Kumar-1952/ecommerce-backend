package com.anuj.ecommerce_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_images")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductImage extends BaseEntity {

    @Column(nullable = false, length = 1000)
    private String imageUrl;

    @Builder.Default
    @Column(nullable = false)
    private Boolean primaryImage = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variant_id")
    private ProductVariant variant;

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;

        if (!(o instanceof ProductImage image))
            return false;

        return getId() != null && getId().equals(image.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}