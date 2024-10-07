package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.aop.IntroductionAdvisor;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Integer id;
    private String title;
    private String details;

    public Product(Optional<Product> product) {
    }

    public Integer getId() {
        return id;
    }

}
