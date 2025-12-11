package com.example.ecommerceplatform.repository;

import com.example.ecommerceplatform.entity.Cart;
import com.example.ecommerceplatform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}