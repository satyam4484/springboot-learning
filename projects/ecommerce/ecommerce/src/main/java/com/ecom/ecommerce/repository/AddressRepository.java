package com.ecom.ecommerce.repository;

import com.ecom.ecommerce.entity.Address;
import com.ecom.ecommerce.entity.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUser(User user);
}
