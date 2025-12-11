package com.example.ecommerceplatform.service;

import com.example.ecommerceplatform.entity.*;
import com.example.ecommerceplatform.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartService cartService;

    @Transactional
    public void createOrder(String username) {
        // 1. 获取购物车
        Cart cart = cartService.getCartByUsername(username);

        // 务必复制一份 list，防止后续操作出现并发修改异常
        List<CartItem> cartItems = new ArrayList<>(cart.getItems());

        if (cartItems.isEmpty()) {
            throw new RuntimeException("购物车为空，不能下单");
        }

        // 2. 创建订单头
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setCreateTime(LocalDateTime.now());
        order.setStatus("已付款");

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        // 3. 循环处理每一项
        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();

            // 校验库存
            if (product.getStock() < cartItem.getQuantity()) {
                throw new RuntimeException("商品 " + product.getName() + " 库存不足");
            }

            // 扣减库存 (事务会自动提交更新到数据库)
            product.setStock(product.getStock() - cartItem.getQuantity());

            // 创建订单明细
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(product.getPrice());

            orderItems.add(orderItem);
            total = total.add(product.getPrice().multiply(new BigDecimal(cartItem.getQuantity())));
        }

        order.setOrderItems(orderItems);
        order.setTotalAmount(total);

        // 4. 保存订单
        orderRepository.save(order);

        // 5. 安全清空购物车
        cartService.clearCart(username);
    }

    public List<Order> findUserOrders(User user) {
        return orderRepository.findByUser(user);
    }

    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }
}