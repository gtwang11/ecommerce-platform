package com.example.ecommerceplatform.controller;

import com.example.ecommerceplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    // 登录页面
    @GetMapping("/login")
    public String login() {
        return "login"; // 指向 login.html
    }

    // 注册页面
    @GetMapping("/register")
    public String register() {
        return "register"; // 指向 register.html
    }

    // 处理注册请求
    @PostMapping("/register")
    public String processRegister(@RequestParam String username,
                                  @RequestParam String password,
                                  @RequestParam String email) {
        userService.registerUser(username, password, email);
        return "redirect:/login?registered"; // 注册成功跳到登录
    }
}