package com.github.sozinhos.ecommerce.orders.exceptions;

import org.springframework.http.HttpStatus;

public class ProductServiceUnavailableException extends BaseException {
    public ProductServiceUnavailableException() {
        super("Product service unavailable", HttpStatus.SERVICE_UNAVAILABLE);
    }
}
