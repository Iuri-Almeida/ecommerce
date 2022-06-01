package com.github.sozinhos.ecommerce.orders.exceptions;

import org.springframework.http.HttpStatus;

public class RequestHasNoUserIdException extends BaseException {
    public RequestHasNoUserIdException() {
        super("Request has no user id", HttpStatus.BAD_REQUEST);
    }
}
