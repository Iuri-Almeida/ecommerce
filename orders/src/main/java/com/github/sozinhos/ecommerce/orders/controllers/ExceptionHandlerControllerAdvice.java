package com.github.sozinhos.ecommerce.orders.controllers;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import com.github.sozinhos.ecommerce.orders.exceptions.BaseException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<StandardError> baseExceptionHandler(BaseException e, HttpServletRequest request) {
        int status = e.getStatus().value();

        StandardError error = StandardError.builder()
            .timestamp(Instant.now())
            .status(status)
            .message(e.getMessage())
            .path(request.getRequestURI())
            .build();

        return ResponseEntity.status(status).body(error);
    }
}
