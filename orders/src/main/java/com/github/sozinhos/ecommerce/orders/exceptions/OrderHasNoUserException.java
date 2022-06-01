package com.github.sozinhos.ecommerce.orders.exceptions;

import org.springframework.http.HttpStatus;

public class OrderHasNoUserException extends BaseException {
    public OrderHasNoUserException() {
        super("Order has no user", HttpStatus.BAD_REQUEST);
    }
}
