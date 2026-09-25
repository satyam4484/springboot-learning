package com.ecom.ecommerce.controller;

import com.ecom.ecommerce.dto.ApiResponse;
import com.ecom.ecommerce.dto.CreateOrderRequest;
import com.ecom.ecommerce.dto.OrderDto;
import com.ecom.ecommerce.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse<OrderDto>> createOrder(
            @Valid @RequestBody CreateOrderRequest request,
            Authentication authentication) {

        OrderDto order =
                orderService.createOrder(
                        request,
                        authentication
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ApiResponse.success(
                                "Order created successfully",
                                "/api/orders",
                                order
                        )
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderDto>> getOrderById(
            @PathVariable Long id,
            Authentication authentication) {

        OrderDto order =
                orderService.getOrderById(
                        id,
                        authentication
                );

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Order fetched successfully",
                        "/api/orders/" + id,
                        order
                )
        );
    }

    @GetMapping("/my-orders")
    public ResponseEntity<ApiResponse<List<OrderDto>>> getMyOrders(
            Authentication authentication) {

        List<OrderDto> orders =
                orderService.getMyOrders(authentication);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Orders fetched successfully",
                        "/api/orders/my-orders",
                        orders
                )
        );
    }
}