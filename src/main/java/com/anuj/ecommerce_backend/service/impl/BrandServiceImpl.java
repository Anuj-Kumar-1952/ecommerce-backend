package com.anuj.ecommerce_backend.service.impl;

import com.anuj.ecommerce_backend.dto.request.BrandRequest;
import com.anuj.ecommerce_backend.dto.response.BrandResponse;
import com.anuj.ecommerce_backend.entity.Brand;
import com.anuj.ecommerce_backend.exception.BadRequestException;
import com.anuj.ecommerce_backend.exception.ResourceNotFoundException;
import com.anuj.ecommerce_backend.mapper.BrandMapper;
import com.anuj.ecommerce_backend.repository.BrandRepository;
import com.anuj.ecommerce_backend.service.BrandService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

        private final BrandRepository brandRepository;

        private final BrandMapper brandMapper;

        @Transactional
        @Override
        public BrandResponse create(BrandRequest request) {

                log.info("Creating brand {}", request.getName());

                if (brandRepository.existsByName(request.getName())) {

                        throw new BadRequestException("Brand already exists");
                }

                Brand brand = Brand.builder()
                                .name(request.getName())
                                .description(request.getDescription())
                                .logoUrl(request.getLogoUrl())
                                .build();

                Brand savedBrand = brandRepository.save(brand);

                return brandMapper.toResponse(savedBrand);
        }

        @Override
        public List<BrandResponse> getAll() {

                return brandRepository
                                .findByActiveTrue()
                                .stream()
                                .map(brandMapper::toResponse)
                                .toList();
        }

        @Override
        public BrandResponse getById(Long id) {

                Brand brand = brandRepository
                                .findByIdAndActiveTrue(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Brand not found"));

                return brandMapper.toResponse(brand);
        }

        @Transactional
        @Override
        public BrandResponse update(Long id, BrandRequest request) {

                Brand brand = brandRepository
                                .findByIdAndActiveTrue(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Brand not found"));

                brand.setName(request.getName());

                brand.setDescription(request.getDescription());

                brand.setLogoUrl(request.getLogoUrl());

                return brandMapper.toResponse(brandRepository.save(brand));
        }

        @Transactional
        @Override
        public void delete(Long id) {

                Brand brand = brandRepository
                                .findByIdAndActiveTrue(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Brand not found"));

                brand.setActive(false);

                brandRepository.save(brand);

                log.info("Brand deleted {}", id);
        }
}