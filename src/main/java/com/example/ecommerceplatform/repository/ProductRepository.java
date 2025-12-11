package com.example.ecommerceplatform.repository;

import com.example.ecommerceplatform.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // 基础的增删改查由 JpaRepository 自动提供，无需写代码
}