package com.example.demo.service;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultProductService implements InterfaceProductService {
    private final ProductRepository productRepository;

    @Override
    public List<Product> findAllProducts() {
        return this.productRepository.findAll();
    }

    @Override
    public Product createProduct(String title, String details) {
        return this.productRepository.save(new Product(null, title, details));
    }

    @Override
    public Optional<Product> findProduct(int productId) {
        return this.productRepository.findById(productId);
    }

    @Override
    public void updateProduct(int id, String title, String details) {
         this.productRepository.findById(id)
                .ifPresentOrElse(product ->
                { product.setDetails(details);
                product.setTitle(title);
                },() ->{
                    throw new NoSuchElementException();
                });
    }

    @Override
    public void deleteProduct(int Id) {
        this.productRepository.deleteById(Id);

    }
}
