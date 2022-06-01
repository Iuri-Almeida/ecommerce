package com.github.sozinhos.ecommerce.orders.exceptions;

import org.springframework.http.HttpStatus;

public class OrderNotFoundException extends BaseException {
    public OrderNotFoundException() {
        super("Order not found", HttpStatus.NOT_FOUND);
    }
}
