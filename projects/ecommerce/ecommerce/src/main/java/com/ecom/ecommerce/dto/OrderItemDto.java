package com.ecom.ecommerce.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class OrderItemDto {

  private Long id;

  private Long productId;

  private String productName;

  private Integer quantity;

  private BigDecimal price;

  private BigDecimal subtotal;
}
