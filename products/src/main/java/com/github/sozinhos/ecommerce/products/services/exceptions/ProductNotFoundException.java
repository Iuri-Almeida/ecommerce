package com.github.sozinhos.ecommerce.products.services.exceptions;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String msg) {
        super(msg);
    }
}
