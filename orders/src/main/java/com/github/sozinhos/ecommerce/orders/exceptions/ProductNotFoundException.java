package com.github.sozinhos.ecommerce.orders.exceptions;

import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends BaseException {
    public ProductNotFoundException() {
        super("Product not found", HttpStatus.NOT_FOUND);
    }
}
