package com.github.sozinhos.ecommerce.orders.services;

import java.util.List;

import com.github.sozinhos.ecommerce.orders.clients.ProductServiceClient;
import com.github.sozinhos.ecommerce.orders.entities.Product;
import com.github.sozinhos.ecommerce.orders.exceptions.ProductInsufficientAmountException;
import com.github.sozinhos.ecommerce.orders.exceptions.ProductNotFoundException;
import com.github.sozinhos.ecommerce.orders.exceptions.ProductServiceUnavailableException;

import org.springframework.stereotype.Service;

import feign.FeignException.FeignClientException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductServiceClient productServiceClient;

    public List<Product> checkStock(List<Product> products) {
        try {
            return productServiceClient.batchCheck(products);
        } catch (FeignClientException e) {
            switch (e.status()) {
                case 404:
                    throw new ProductNotFoundException();
            
                case 400:
                    throw new ProductInsufficientAmountException();
                
                default:
                    throw new ProductServiceUnavailableException();
            }
        } catch (Exception e) {
            throw new ProductServiceUnavailableException();
        }
    }

    public void batchUpdate(List<Product> products) {
        try {
            productServiceClient.batchUpdate(products);
        } catch (FeignClientException e) {
            switch (e.status()) {
                case 404:
                    throw new ProductNotFoundException();
            
                case 400:
                    throw new ProductInsufficientAmountException();
                
                default:
                    throw new ProductServiceUnavailableException();
            }
        } catch (Exception e) {
            throw new ProductServiceUnavailableException();
        }
    }
}
