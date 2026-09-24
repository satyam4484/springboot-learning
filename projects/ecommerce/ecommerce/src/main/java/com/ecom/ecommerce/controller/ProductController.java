package com.ecom.ecommerce.controller;

import com.ecom.ecommerce.dto.ApiResponse;
import com.ecom.ecommerce.dto.ProductDto;
import com.ecom.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ApiResponse<ProductDto>> createProduct(
            @Valid @RequestBody ProductDto productDto) {

        ProductDto createdProduct =
                productService.createProduct(productDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ApiResponse.success(
                                "Product created successfully",
                                "/api/products",
                                createdProduct
                        )
                );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductDto>>> getAllProducts() {

        List<ProductDto> products =
                productService.getAllProducts();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Products fetched successfully",
                        "/api/products",
                        products
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDto>> getProductById(
            @PathVariable Long id) {

        ProductDto product =
                productService.getProductById(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Product fetched successfully",
                        "/api/products/" + id,
                        product
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDto>> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductDto productDto) {

        ProductDto updatedProduct =
                productService.updateProduct(id, productDto);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Product updated successfully",
                        "/api/products/" + id,
                        updatedProduct
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(
            @PathVariable Long id) {

        productService.deleteProduct(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Product deleted successfully",
                        "/api/products/" + id,
                        null
                )
        );
    }
}