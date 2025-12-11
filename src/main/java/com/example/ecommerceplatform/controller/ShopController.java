package com.example.ecommerceplatform.controller;

// ▼▼▼▼▼▼▼▼▼▼ 核心修复区域：补全了缺少的 import ▼▼▼▼▼▼▼▼▼▼
import com.example.ecommerceplatform.entity.Order; // 修复找不到符号 Order
import com.example.ecommerceplatform.entity.User;  // 修复找不到符号 User
import java.util.List;                               // 修复找不到符号 List
// ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲

import com.example.ecommerceplatform.service.CartService;
import com.example.ecommerceplatform.service.OrderService;
import com.example.ecommerceplatform.service.ProductService;
import com.example.ecommerceplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ShopController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    // 首页：展示商品
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("products", productService.findAllProducts());
        return "index";
    }

    // 加入购物车
    @PostMapping("/cart/add")
    public String addToCart(@RequestParam Long productId,
                            @AuthenticationPrincipal UserDetails userDetails) {
        // 如果未登录，跳转登录页
        if (userDetails == null) {
            return "redirect:/login";
        }
        cartService.addToCart(userDetails.getUsername(), productId, 1);
        return "redirect:/cart";
    }

    // 查看购物车
    @GetMapping("/cart")
    public String viewCart(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        // 防止未登录报错
        if (userDetails == null) return "redirect:/login";

        model.addAttribute("cart", cartService.getCartByUsername(userDetails.getUsername()));
        return "cart";
    }

    // 结账下单
    @PostMapping("/checkout")
    public String checkout(@AuthenticationPrincipal UserDetails userDetails) {
        orderService.createOrder(userDetails.getUsername());
        return "redirect:/orders";
    }

    // 查看订单历史
    @GetMapping("/orders")
    public String viewOrders(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) return "redirect:/login";

        // 1. 查找当前登录的用户实体 (注意：这里需要 UserService 里有 findByUsername 方法)
        User user = userService.findByUsername(userDetails.getUsername());

        // 2. 查找该用户的订单列表
        List<Order> orders = orderService.findUserOrders(user);

        // 3. 传给前端页面
        model.addAttribute("orders", orders);
        return "order_history";
    }

    @PostMapping("/cart/update")
    public String updateCart(@RequestParam Long productId,
                             @RequestParam int quantity,
                             @AuthenticationPrincipal UserDetails userDetails) {
        cartService.updateQuantity(userDetails.getUsername(), productId, quantity);
        return "redirect:/cart";
    }
}