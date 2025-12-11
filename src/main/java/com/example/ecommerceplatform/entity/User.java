package com.example.ecommerceplatform.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users") // 表名 users
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username; // 用户名

    @Column(nullable = false)
    private String password; // 密码

    private String email;    // 邮箱 (用于发送确认邮件)

    private String role;     // 角色: "ROLE_USER" (顾客) 或 "ROLE_ADMIN" (销售/管理员)
}