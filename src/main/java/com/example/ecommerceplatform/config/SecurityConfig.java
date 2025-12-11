package com.example.ecommerceplatform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        // 放行静态资源和主页、注册页
                        .requestMatchers("/", "/index", "/register", "/css/**", "/js/**", "/images/**").permitAll()
                        // 只有管理员才能访问后台管理
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        // 其他所有请求都需要登录
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login") // 自定义登录页 URL
                        .defaultSuccessUrl("/", true) // 登录成功跳转首页
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll());

        return http.build();
    }

    // 密码加密器 (数据库里不能存明文密码)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}