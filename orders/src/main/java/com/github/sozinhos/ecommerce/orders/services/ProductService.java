package com.github.sozinhos.ecommerce.orders.services;

import java.util.List;

import com.github.sozinhos.ecommerce.orders.entities.Product;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "PRODUCTS-SERVICE")
public interface ProductService {
    @RequestMapping(value = "/products/check", method = RequestMethod.POST)
    void checkStock(List<Product> products);
}
