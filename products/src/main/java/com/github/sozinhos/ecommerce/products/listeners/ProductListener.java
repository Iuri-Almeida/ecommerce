package com.github.sozinhos.ecommerce.products.listeners;

import com.github.sozinhos.ecommerce.products.entities.Order;
import com.github.sozinhos.ecommerce.products.entities.Product;
import com.github.sozinhos.ecommerce.products.services.ProductService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class ProductListener {

    private final ProductService productService;

    public ProductListener(ProductService productService) {
        this.productService = productService;
    }

    @Transactional
    @RabbitListener(queues = "payments.products")
    public void listener(Order order) {
        for (Product product : order.getProducts()) {
            productService.update(product, 1);
        }
    }
}
