package com.github.sozinhos.ecommerce.orders.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Product insufficiente amount")
public class ProductInsufficientAmountException extends RuntimeException {
}
