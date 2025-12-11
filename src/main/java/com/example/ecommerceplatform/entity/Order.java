package com.example.ecommerceplatform.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 关联用户 (多对一)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime createTime; // 下单时间
    private BigDecimal totalAmount;   // 总金额
    private String status;            // 状态: 待付款, 已发货, 已完成

    // 关联订单详情
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;
}