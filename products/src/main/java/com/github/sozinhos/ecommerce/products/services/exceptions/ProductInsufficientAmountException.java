package com.github.sozinhos.ecommerce.products.services.exceptions;

public class ProductInsufficientAmountException extends RuntimeException {

    public ProductInsufficientAmountException(String msg) {
        super(msg);
    }

}
