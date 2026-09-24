package com.ecom.ecommerce.repository;

import com.ecom.ecommerce.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {}
