package com.ecom.ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductCacheEvictionService {

    @CacheEvict(value = "products", key = "#productId")
    public void evictProduct(Long productId) {
    }
}