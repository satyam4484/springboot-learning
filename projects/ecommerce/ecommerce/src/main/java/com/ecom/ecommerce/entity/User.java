package com.ecom.ecommerce.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String passwordHash;

  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;
  
  @Column(nullable = false)
  private String role;

  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
  }
}
