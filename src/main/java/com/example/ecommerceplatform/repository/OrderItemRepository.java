package com.example.ecommerceplatform.repository;

import com.example.ecommerceplatform.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    // ★★★ 新增：根据商品ID，强制删除所有的订单明细 ★★★
    @Transactional
    @Modifying
    void deleteByProductId(Long productId);
}