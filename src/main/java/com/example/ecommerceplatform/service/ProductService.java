package com.example.ecommerceplatform.service;

import com.example.ecommerceplatform.entity.Product;
import com.example.ecommerceplatform.repository.CartItemRepository;
import com.example.ecommerceplatform.repository.OrderItemRepository;
import com.example.ecommerceplatform.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired // 记得注入这个新的 Repository
    private CartItemRepository cartItemRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    // 查找所有商品
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    // 保存商品 (用于初始化数据或管理员添加)
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    // 根据ID查找商品
    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteProduct(Long id) {
        // 1. 第一步：先清空所有人【购物车】里的这个商品
        cartItemRepository.deleteByProductId(id);

        // 2. 第二步：再清空所有人【历史订单】里的这个商品
        // (注意：这意味着用户的订单里会凭空少一个东西，总价可能对不上，但在实验中这是允许的)
        orderItemRepository.deleteByProductId(id);

        // 3. 第三步：现在没有任何牵挂了，物理删除商品
        productRepository.deleteById(id);
    }
}