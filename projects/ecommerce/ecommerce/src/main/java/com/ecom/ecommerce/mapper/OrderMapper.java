package com.ecom.ecommerce.mapper;

import com.ecom.ecommerce.dto.OrderDto;
import com.ecom.ecommerce.dto.OrderItemDto;
import com.ecom.ecommerce.entity.Order;
import com.ecom.ecommerce.entity.OrderItem;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderMapper {

  private final ModelMapper modelMapper;

  public OrderDto toDto(Order order) {

    OrderDto dto = modelMapper.map(order, OrderDto.class);

    dto.setUserId(order.getUser().getId());

    dto.setItems(order.getOrderItems().stream().map(this::toItemDto).toList());

    return dto;
  }

  private OrderItemDto toItemDto(OrderItem orderItem) {

    OrderItemDto dto = modelMapper.map(orderItem, OrderItemDto.class);

    dto.setProductId(orderItem.getProduct().getId());
    dto.setProductName(orderItem.getProduct().getName());

    dto.setSubtotal(orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())));

    return dto;
  }
}
