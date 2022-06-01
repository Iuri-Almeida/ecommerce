package com.github.sozinhos.ecommerce.orders.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.SERVICE_UNAVAILABLE, reason = "Product service unavailable")
public class ProductServiceUnavailableException extends RuntimeException {
}
