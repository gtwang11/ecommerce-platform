package com.example.ecommerceplatform.service;

import com.example.ecommerceplatform.entity.*;
import com.example.ecommerceplatform.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    // 获取当前用户的购物车，如果没有就新建一个
    public Cart getCartByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        return cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setUser(user);
                    return cartRepository.save(cart);
                });
    }

    // 添加商品到购物车
    @Transactional
    public void addToCart(String username, Long productId, int quantity) {
        Cart cart = getCartByUsername(username);
        Product product = productRepository.findById(productId).orElseThrow();

        // 检查购物车里是不是已经有这个商品了
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            cart.getItems().add(newItem);
        }
        cartRepository.save(cart);
    }

    // 清空购物车 (下单后调用)
    @Transactional
    public void clearCart(String username) {
        Cart cart = getCartByUsername(username);
        cart.getItems().clear(); // 清空列表
        cartRepository.save(cart); // 保存状态，orphanRemoval=true 会自动删除数据库里的记录
    }

    @Transactional
    public void updateQuantity(String username, Long productId, int quantity) {
        Cart cart = getCartByUsername(username);
        cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(item -> {
                    // 如果数量小于等于0，则删除该商品
                    if (quantity <= 0) {
                        cart.getItems().remove(item);
                    } else {
                        // 检查库存是否足够（可选，为了严谨最好加上）
                        if (quantity <= item.getProduct().getStock()) {
                            item.setQuantity(quantity);
                        }
                    }
                });
        cartRepository.save(cart);
    }
}