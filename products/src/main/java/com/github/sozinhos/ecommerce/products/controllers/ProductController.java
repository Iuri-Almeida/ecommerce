package com.github.sozinhos.ecommerce.products.controllers;

import com.github.sozinhos.ecommerce.products.entities.Product;
import com.github.sozinhos.ecommerce.products.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Product> insert(@RequestBody Product product) {
        product = productService.insert(product);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(product.getId()).toUri();
        return ResponseEntity.created(uri).body(product);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> batchUpdate(@RequestBody List<Product> products) {
        productService.batchUpdate(products);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/check")
    public ResponseEntity<Void> batchCheck(@RequestBody List<Product> products) {
        productService.batchCheck(products);
        return ResponseEntity.noContent().build();
    }

}
