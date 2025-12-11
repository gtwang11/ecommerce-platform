package com.example.ecommerceplatform.config;

import com.example.ecommerceplatform.entity.Product;
import com.example.ecommerceplatform.entity.User;
import com.example.ecommerceplatform.repository.ProductRepository;
import com.example.ecommerceplatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

@Configuration
public class DataInitializer {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initData(ProductRepository productRepo, UserRepository userRepo) {
        return args -> {
            // 1. 初始化商品数据 (解决首页无商品问题)
            if (productRepo.count() == 0) {
                Product p1 = new Product();
                p1.setName("iPhone 15 Pro");
                p1.setDescription("钛金属设计，A17 Pro 芯片，强大的摄影系统。");
                p1.setPrice(new BigDecimal("7999.00"));
                p1.setStock(100);
                p1.setImageUrl("https://images.unsplash.com/photo-1695048133142-1a20484d2569?auto=format&fit=crop&w=500&q=60");
                productRepo.save(p1);

                Product p2 = new Product();
                p2.setName("MacBook Air M2");
                p2.setDescription("轻薄便携，超长续航，M2 芯片加持。");
                p2.setPrice(new BigDecimal("8999.00"));
                p2.setStock(50);
                p2.setImageUrl("https://images.unsplash.com/photo-1611186871348-b1ce696e52c9?auto=format&fit=crop&w=500&q=60");
                productRepo.save(p2);

                Product p3 = new Product();
                p3.setName("Sony WH-1000XM5");
                p3.setDescription("行业领先的降噪耳机，舒适佩戴体验。");
                p3.setPrice(new BigDecimal("2499.00"));
                p3.setStock(200);
                p3.setImageUrl("https://images.unsplash.com/photo-1618366712010-f4ae9c647dcb?auto=format&fit=crop&w=500&q=60");
                productRepo.save(p3);
            }

            // 2. 初始化管理员账号 (解决管理员登录问题)
            if (userRepo.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123")); // 密码 admin123
                admin.setEmail("admin@shop.com");
                admin.setRole("ROLE_ADMIN"); // 关键：设置角色为 ADMIN
                userRepo.save(admin);
                System.out.println(">>> 管理员账号已创建: admin / admin123");
            }

            // 3. 初始化一个普通测试用户
            if (userRepo.findByUsername("user").isEmpty()) {
                User user = new User();
                user.setUsername("user");
                user.setPassword(passwordEncoder.encode("123456"));
                user.setEmail("user@shop.com");
                user.setRole("ROLE_USER");
                userRepo.save(user);
            }
        };
    }
}