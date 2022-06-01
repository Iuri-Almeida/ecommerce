package com.github.sozinhos.ecommerce.orders.exceptions;

import org.springframework.http.HttpStatus;

public class ProductInsufficientAmountException extends BaseException {
    public ProductInsufficientAmountException() {
        super("Product insufficiente amount", HttpStatus.BAD_REQUEST);
    }
}
