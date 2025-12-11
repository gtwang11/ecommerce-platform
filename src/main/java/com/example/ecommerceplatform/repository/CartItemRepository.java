package com.example.ecommerceplatform.repository;

import com.example.ecommerceplatform.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    // 增加这个方法：根据商品ID删除购物车记录
    // @Modifying 说明这是个修改/删除操作
    @Transactional
    @Modifying
    void deleteByProductId(Long productId);
}