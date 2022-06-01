package com.github.sozinhos.ecommerce.products.services;

import com.github.sozinhos.ecommerce.products.entities.Product;
import com.github.sozinhos.ecommerce.products.repositories.ProductRepository;
import com.github.sozinhos.ecommerce.products.services.exceptions.ProductDatabaseException;
import com.github.sozinhos.ecommerce.products.services.exceptions.ProductInsufficientAmountException;
import com.github.sozinhos.ecommerce.products.services.exceptions.ProductNotFoundException;
import com.github.sozinhos.ecommerce.products.services.exceptions.ProductNullParameterException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isEmpty()) {
            throw new ProductNotFoundException("Could not find product with id = " + productId);
        }
        return product.get();
    }

    public Product insert(Product product) {
        if (product.getName() == null || product.getPrice() == null || product.getAmount() == null) {
            throw new ProductNullParameterException("Parameter with null value");
        }
        return productRepository.save(product);
    }

    public void delete(Long productId) {
        try {
            productRepository.deleteById(productId);
        } catch (EmptyResultDataAccessException e) {
            throw new ProductNotFoundException("Could not find product with id = " + productId);
        } catch (DataIntegrityViolationException e) {
            throw new ProductDatabaseException("Product with other dependencies in database");
        }
    }

    public void update(Product product, int factor) {
        Product dbProduct = this.findById(product.getId());
        if (product.getAmount() != null) {
            if (dbProduct.getAmount() < product.getAmount()) {
                throw new ProductInsufficientAmountException("Product '" + product.getName() + "' with insufficient amount");
            }
            dbProduct.setAmount(dbProduct.getAmount() + product.getAmount() * factor);
        }
        productRepository.save(dbProduct);
    }

    @Transactional
    public void batchUpdate(List<Product> products) {
        for (Product product : products) {
            this.update(product, -1);
        }
    }

    public void batchCheck(List<Product> products) {
        for (Product product : products) {
            Product dbProduct = this.findById(product.getId());
            if (dbProduct.getAmount() < product.getAmount()) {
                throw new ProductInsufficientAmountException("Product '" + product.getName() + "' with insufficient amount");
            }
        }
    }
}
