package com.github.sozinhos.ecommerce.products.controllers.exceptions;

import com.github.sozinhos.ecommerce.products.services.exceptions.ProductDatabaseException;
import com.github.sozinhos.ecommerce.products.services.exceptions.ProductInsufficientAmountException;
import com.github.sozinhos.ecommerce.products.services.exceptions.ProductNotFoundException;
import com.github.sozinhos.ecommerce.products.services.exceptions.ProductNullParameterException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ProductExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<StandardError> productNotFound(ProductNotFoundException e, HttpServletRequest request) {
        String error = "Product not found";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(ProductNullParameterException.class)
    public ResponseEntity<StandardError> productNullParameter(ProductNullParameterException e, HttpServletRequest request) {
        String error = "Parameter null";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(ProductDatabaseException.class)
    public ResponseEntity<StandardError> productDatabaseException(ProductDatabaseException e, HttpServletRequest request) {
        String error = "Database error";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(ProductInsufficientAmountException.class)
    public ResponseEntity<StandardError> productInsufficientAmount(ProductInsufficientAmountException e, HttpServletRequest request) {
        String error = "Insufficient amount";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

}
