package com.github.sozinhos.ecommerce.products.controllers;

import java.util.List;

import com.github.sozinhos.ecommerce.products.entities.Product;
import com.github.sozinhos.ecommerce.products.services.ProductService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/batch")
public class BatchController {
    private final ProductService productService;

    public BatchController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(value = "/check")
    public ResponseEntity<List<Product>> batchCheck(@RequestBody List<Product> products) {
        return ResponseEntity.ok(productService.batchCheck(products));
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Void> batchUpdate(@RequestBody List<Product> products) {
        productService.batchUpdate(products);
        return ResponseEntity.noContent().build();
    }
}
