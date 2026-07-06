package com.anuj.ecommerce_backend.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anuj.ecommerce_backend.dto.request.ProductImageRequest;
import com.anuj.ecommerce_backend.dto.request.ProductRequest;
import com.anuj.ecommerce_backend.dto.request.ProductVariantRequest;
import com.anuj.ecommerce_backend.dto.response.ProductResponse;
import com.anuj.ecommerce_backend.entity.Brand;
import com.anuj.ecommerce_backend.entity.Category;
import com.anuj.ecommerce_backend.entity.Product;
import com.anuj.ecommerce_backend.entity.ProductImage;
import com.anuj.ecommerce_backend.entity.ProductVariant;
import com.anuj.ecommerce_backend.enums.ProductStatus;
import com.anuj.ecommerce_backend.exception.BadRequestException;
import com.anuj.ecommerce_backend.exception.ResourceNotFoundException;
import com.anuj.ecommerce_backend.mapper.ProductMapper;
import com.anuj.ecommerce_backend.repository.BrandRepository;
import com.anuj.ecommerce_backend.repository.CategoryRepository;
import com.anuj.ecommerce_backend.repository.ProductRepository;
import com.anuj.ecommerce_backend.service.ProductService;
import com.anuj.ecommerce_backend.util.SkuGenerator;
import com.anuj.ecommerce_backend.util.SlugGenerator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final BrandRepository brandRepository;

    private final ProductMapper productMapper;

    private final SlugGenerator slugGenerator;

    private final SkuGenerator skuGenerator;

    @Transactional
    @Override
    public ProductResponse create(ProductRequest request) {

        log.info("Creating product {}", request.getName());

        // Product validation
        if (productRepository.existsByName(request.getName())) {

            throw new BadRequestException("Product already exists");
        }

        // Category validation
        Category category = categoryRepository.findByIdAndActiveTrue(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        // Brand validation
        Brand brand = brandRepository.findByIdAndActiveTrue(request.getBrandId())
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found"));

        // Generate slug
        String slug = slugGenerator.generate(request.getName());

        if (productRepository.existsBySlug(slug)) {

            throw new BadRequestException("Slug already exists");
        }

        // Create product
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .slug(slug)
                .category(category)
                .brand(brand)
                .status(ProductStatus.ACTIVE)
                .build();

        // Create variants
        for (ProductVariantRequest variantRequest : request.getVariants()) {

            // Validate primary image
            int primaryCount = 0;

            for (ProductImageRequest imageRequest : variantRequest.getImages()) {

                if (Boolean.TRUE.equals(imageRequest.getPrimaryImage())) {
                    primaryCount++;
                }
            }

            if (primaryCount != 1) {

                throw new BadRequestException("Each variant must have exactly one primary image");
            }

            // Generate SKU
            String sku = skuGenerator.generate(
                    brand.getName(),
                    product.getName(),
                    variantRequest.getStorage(),
                    variantRequest.getColor());

            ProductVariant variant = ProductVariant.builder()
                    .sku(sku)
                    .price(variantRequest.getPrice())
                    .discountPrice(variantRequest.getDiscountPrice())
                    .stock(variantRequest.getStock())
                    .color(variantRequest.getColor())
                    .storage(variantRequest.getStorage())
                    .product(product)
                    .build();

            // images
            for (ProductImageRequest imageRequest : variantRequest.getImages()) {

                ProductImage image = ProductImage.builder()
                        .imageUrl(imageRequest.getImageUrl())
                        .primaryImage(imageRequest.getPrimaryImage())
                        .variant(variant)
                        .build();

                variant.getImages().add(image);
            }

            product.getVariants().add(variant);
        }

        Product savedProduct = productRepository.save(product);

        log.info("Product created successfully {}", savedProduct.getId());

        return productMapper.toResponse(savedProduct);
    }

    @Override
    public List<ProductResponse> getAll() {

        log.info("Fetching all products");

        return productRepository
                .findByActiveTrue()
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }

    @Override
    public ProductResponse getById(Long id) {

        log.info("Fetching product {}", id);

        Product product = productRepository
                .findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        return productMapper.toResponse(product);
    }

    @Transactional
    @Override
    public void delete(Long id) {

        Product product = productRepository
                .findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        product.setActive(false);

        productRepository.save(product);

        log.info("Product deleted {}", id);
    }
}