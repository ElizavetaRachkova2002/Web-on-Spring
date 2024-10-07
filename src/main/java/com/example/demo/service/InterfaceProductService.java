package com.example.demo.service;

import com.example.demo.entity.Product;
import java.util.List;
import java.util.Optional;

public interface InterfaceProductService {
    List<Product> findAllProducts();

    Product createProduct(String title, String details);

    Optional<Product> findProduct(int productId);

    void updateProduct(int id, String title, String details);

    void deleteProduct(int Id);
}
