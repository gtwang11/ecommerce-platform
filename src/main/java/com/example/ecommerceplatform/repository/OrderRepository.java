package com.example.ecommerceplatform.repository;

import com.example.ecommerceplatform.entity.Order;
import com.example.ecommerceplatform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user); // 查某个用户的历史订单
}