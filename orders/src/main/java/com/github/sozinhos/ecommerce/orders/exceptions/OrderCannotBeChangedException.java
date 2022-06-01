package com.github.sozinhos.ecommerce.orders.exceptions;

import org.springframework.http.HttpStatus;

public class OrderCannotBeChangedException extends BaseException {
    public OrderCannotBeChangedException() {
        super("The order cannot be changed", HttpStatus.BAD_REQUEST);
    }
}
