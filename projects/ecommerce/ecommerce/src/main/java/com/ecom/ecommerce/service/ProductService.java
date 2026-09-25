package com.ecom.ecommerce.service;

import com.ecom.ecommerce.constant.ErrorCode;
import com.ecom.ecommerce.dto.ProductDto;
import com.ecom.ecommerce.entity.Category;
import com.ecom.ecommerce.entity.Product;
import com.ecom.ecommerce.exception.ApplicationException;
import com.ecom.ecommerce.repository.CategoryRepository;
import com.ecom.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @CacheEvict(value = "productList", key = "'all'")
    @Transactional
    public ProductDto createProduct(ProductDto productDto) {

        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new ApplicationException(
                        "Category not found with id: "
                                + productDto.getCategoryId(),
                        ErrorCode.CATEGORY_NOT_FOUND,
                        HttpStatus.NOT_FOUND));

        Product product = modelMapper.map(productDto, Product.class);

        product.setCategory(category);

        Product savedProduct = productRepository.save(product);

        return toDto(savedProduct);
    }

    @Cacheable(value = "productList", key = "'all'")
    @Transactional(readOnly = true)
    public List<ProductDto> getAllProducts() {
        System.out.println("Fetching all products from database");

        return productRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "products", key = "#id")
    public ProductDto getProductById(Long id) {
        System.out.println("Fetching product with id: " + id + " from database");

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(
                        "Product not found with id: " + id,
                        ErrorCode.PRODUCT_NOT_FOUND,
                        HttpStatus.NOT_FOUND));

        return toDto(product);
    }

    @Caching(evict = {
            @CacheEvict(value = "products", key = "#id"),
            @CacheEvict(value = "productList", key = "'all'")
    })
    @Transactional
    public ProductDto updateProduct(
            Long id,
            ProductDto productDto) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(
                        "Product not found with id: " + id,
                        ErrorCode.PRODUCT_NOT_FOUND,
                        HttpStatus.NOT_FOUND));

        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new ApplicationException(
                        "Category not found with id: "
                                + productDto.getCategoryId(),
                        ErrorCode.CATEGORY_NOT_FOUND,
                        HttpStatus.NOT_FOUND));

        modelMapper.map(productDto, product);

        product.setCategory(category);

        Product updatedProduct = productRepository.save(product);

        return toDto(updatedProduct);
    }

    @Caching(evict = {
            @CacheEvict(value = "products", key = "#id"),
            @CacheEvict(value = "productList", key = "'all'")
    })
    @Transactional
    public void deleteProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(
                        "Product not found with id: " + id,
                        ErrorCode.PRODUCT_NOT_FOUND,
                        HttpStatus.NOT_FOUND));

        productRepository.delete(product);
    }

    private ProductDto toDto(Product product) {

        ProductDto productDto = modelMapper.map(
                product,
                ProductDto.class);

        productDto.setCategoryId(product.getCategory().getId());

        return productDto;
    }
}