package com.ecom.ecommerce.dto.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent {

    private Long orderId;
    private Long userId;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
}