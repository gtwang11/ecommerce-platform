package com.example.ecommerceplatform.repository;

import com.example.ecommerceplatform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username); // 用于登录查询
}