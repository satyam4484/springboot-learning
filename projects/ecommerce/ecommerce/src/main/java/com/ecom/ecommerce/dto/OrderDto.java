package com.ecom.ecommerce.dto;

import com.ecom.ecommerce.enums.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDto {

    private Long id;

    private Long userId;

    private OrderStatus status;

    private BigDecimal totalAmount;

    private String shippingAddress;

    private List<OrderItemDto> items;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}