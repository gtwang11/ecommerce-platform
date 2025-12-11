package com.example.ecommerceplatform.controller;

import com.example.ecommerceplatform.entity.Order;
import com.example.ecommerceplatform.entity.Product;
import com.example.ecommerceplatform.service.OrderService;
import com.example.ecommerceplatform.service.ProductService;
import com.example.ecommerceplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/admin") // 所有路径都以 /admin 开头
public class AdminController {

    @Autowired private ProductService productService;
    @Autowired private OrderService orderService;
    @Autowired private UserService userService;

    // 1. 后台仪表盘 (统计报表)
    @GetMapping("")
    public String dashboard(Model model) {
        List<Order> orders = orderService.findAllOrders();

        // 计算总销售额
        BigDecimal totalSales = orders.stream()
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("totalSales", totalSales);
        model.addAttribute("totalOrders", orders.size());
        model.addAttribute("totalUsers", userService.findAllUsers().size());
        model.addAttribute("totalProducts", productService.findAllProducts().size());

        // 加载最近的订单供查看
        model.addAttribute("recentOrders", orders);
        // 加载所有用户供管理
        model.addAttribute("users", userService.findAllUsers());

        return "admin/dashboard";
    }

    // 2. 商品管理页面
    @GetMapping("/products")
    public String productManagement(Model model) {
        model.addAttribute("products", productService.findAllProducts());
        return "admin/products";
    }

    // 3. 添加/编辑商品表单
    @GetMapping("/products/new")
    public String showProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "admin/product_form";
    }

    // 4. 保存商品 (增/改)
    @PostMapping("/products/save")
    public String saveProduct(@ModelAttribute Product product) {
        productService.saveProduct(product);
        return "redirect:/admin/products";
    }

    // 5. 删除商品
    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            productService.deleteProduct(id);
            // 添加成功提示消息
            redirectAttributes.addFlashAttribute("success", "商品删除成功");
        } catch (Exception e) {
            // ★★★ 捕获异常，防止 500 错误页 ★★★
            // 将错误信息传回页面显示
            redirectAttributes.addFlashAttribute("error", "删除失败：" + e.getMessage());
        }
        return "redirect:/admin/products";
    }

    @GetMapping("/products/edit/{id}")
    public String editProduct(@PathVariable Long id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        return "admin/product_form"; // 复用之前的表单页面
    }
}