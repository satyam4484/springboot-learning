package com.ecommerce.ecommerce.projects.employeeService.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.ecommerce.projects.employeeService.entity.User;

public  interface UserRepository  extends JpaRepository<User, Long>{
    Optional<User> findByUsername(String username);
}
