package com.ecom.ecommerce.repository;

import com.ecom.ecommerce.entity.Order;
import com.ecom.ecommerce.entity.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}
