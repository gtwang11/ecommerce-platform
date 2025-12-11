package com.example.ecommerceplatform.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;        // 商品名称

    @Column(length = 1000)      // 增加描述长度，防止报错
    private String description; // 商品描述

    private BigDecimal price;   // 价格
    private String imageUrl;    // 图片链接 (可以是 http://... 或者 /images/...)

    private Integer stock;      // 库存 (这次新增的，用于库存管理)
}