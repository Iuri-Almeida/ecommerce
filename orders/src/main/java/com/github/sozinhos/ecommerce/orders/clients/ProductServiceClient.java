package com.github.sozinhos.ecommerce.orders.clients;

import java.util.List;

import com.github.sozinhos.ecommerce.orders.entities.Product;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "PRODUCTS-SERVICE")
public interface ProductServiceClient {
    @RequestMapping(value = "/batch/check", method = RequestMethod.POST)
    List<Product> batchCheck(List<Product> products);

    @RequestMapping(value = "/batch/update", method = RequestMethod.PUT)
    void batchUpdate(List<Product> products);
}
