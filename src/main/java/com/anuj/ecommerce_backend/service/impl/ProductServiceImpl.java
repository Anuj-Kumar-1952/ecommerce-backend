package com.anuj.ecommerce_backend.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anuj.ecommerce_backend.dto.request.ProductImageRequest;
import com.anuj.ecommerce_backend.dto.request.ProductRequest;
import com.anuj.ecommerce_backend.dto.request.ProductVariantRequest;
import com.anuj.ecommerce_backend.dto.response.ProductPageResponse;
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
import com.anuj.ecommerce_backend.repository.ProductVariantRepository;
import com.anuj.ecommerce_backend.service.ProductService;
import com.anuj.ecommerce_backend.util.SkuGenerator;
import com.anuj.ecommerce_backend.util.SlugGenerator;
import com.anuj.ecommerce_backend.validator.ProductValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductVariantRepository productVariantRepository;

    private final CategoryRepository categoryRepository;

    private final BrandRepository brandRepository;

    private final ProductMapper productMapper;

    private final SlugGenerator slugGenerator;

    private final SkuGenerator skuGenerator;

    private final ProductValidator productValidator;

    private Category getCategory(Long categoryId) {

        return categoryRepository.findByIdAndActiveTrue(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    private Brand getBrand(Long brandId) {

        return brandRepository.findByIdAndActiveTrue(brandId)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found"));
    }

    private String generateSlug(String productName) {

        String slug = slugGenerator.generate(productName);

        if (productRepository.existsBySlug(slug)) {

            throw new BadRequestException("Slug already exists");
        }

        return slug;
    }

    private void createVariants(Product product, Brand brand, List<ProductVariantRequest> requests) {

        for (ProductVariantRequest request : requests) {

            productValidator.validateVariant(request);

            String sku = generateSku(brand.getName(), product.getName(), request);

            ProductVariant variant = ProductVariant.builder()
                    .sku(sku)
                    .price(request.getPrice())
                    .discountPrice(request.getDiscountPrice())
                    .stock(request.getStock())
                    .color(request.getColor())
                    .storage(request.getStorage())
                    .build();

            createImages(variant, request.getImages());

            product.addVariant(variant);
        }
    }

    private String generateSku(String brand, String product, ProductVariantRequest request) {

        String sku = skuGenerator.generate(
                brand,
                product,
                request.getStorage(),
                request.getColor());

        if (productVariantRepository.existsBySku(sku)) {

            throw new BadRequestException("SKU already exists");
        }

        return sku;
    }

    private void createImages(ProductVariant variant, List<ProductImageRequest> requests) {

        for (ProductImageRequest request : requests) {

            ProductImage image = ProductImage.builder()
                    .imageUrl(request.getImageUrl())
                    .primaryImage(request.getPrimaryImage())
                    .build();

            variant.addImage(image);
        }
    }

    @Transactional
    @Override
    public ProductResponse create(ProductRequest request) {

        log.info("Creating product {}", request.getName());

        productValidator.validateProduct(request);

        Category category = getCategory(request.getCategoryId());

        Brand brand = getBrand(request.getBrandId());

        String slug = generateSlug(request.getName());

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .slug(slug)
                .category(category)
                .brand(brand)
                .status(ProductStatus.ACTIVE)
                .build();

        createVariants(product, brand, request.getVariants());

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

        Product product = productRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        product.setActive(false);

        productRepository.save(product);

        log.info("Product deleted {}", id);
    }

    @Override
    public ProductPageResponse search(String keyword, Integer page, Integer size, String sortBy, String direction) {

        log.info("Searching products: keyword={}, page={}, size={}, sortBy={}, direction={}", keyword, page, size,
                sortBy, direction);

        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Product> productPage;

        if (keyword != null && !keyword.isBlank()) {

            productPage = productRepository.findByNameContainingIgnoreCaseAndActiveTrue(keyword, pageable);

        } else {

            productPage = productRepository.findByActiveTrue(pageable);
        }

        return ProductPageResponse
                .builder()
                .content(productPage.getContent()
                        .stream()
                        .map(productMapper::toResponse)
                        .toList())
                .page(productPage.getNumber())
                .size(productPage.getSize())
                .totalElements(productPage.getTotalElements())
                .totalPages(productPage.getTotalPages())
                .last(productPage.isLast())
                .build();
    }
}