package com.anuj.ecommerce_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "brands")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Brand extends BaseEntity {

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(length = 500)
    private String description;

    private String logoUrl;

    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;

        if (!(o instanceof Brand brand))
            return false;

        return getId() != null && getId().equals(brand.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}