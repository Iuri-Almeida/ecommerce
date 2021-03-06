package com.github.sozinhos.ecommerce.orders.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.github.sozinhos.ecommerce.orders.config.RabbitMQConfig;
import com.github.sozinhos.ecommerce.orders.entities.Order;
import com.github.sozinhos.ecommerce.orders.entities.OrderStatus;
import com.github.sozinhos.ecommerce.orders.entities.Product;
import com.github.sozinhos.ecommerce.orders.exceptions.OrderNotFoundException;
import com.github.sozinhos.ecommerce.orders.exceptions.RequestHasNoUserIdException;
import com.github.sozinhos.ecommerce.orders.exceptions.OrderCannotBeChangedException;
import com.github.sozinhos.ecommerce.orders.exceptions.OrderHasNoUserException;
import com.github.sozinhos.ecommerce.orders.repositories.OrderRepository;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final RabbitTemplate template;

    public List<Product> processProducts(List<Product> products) {
        List<Product> resultList = new ArrayList<>();

        if (Objects.isNull(products)) {
            return resultList;
        }

        if (products.size() == 0) {
            return resultList;
        }
        
        Map<Long, Product> cacheProducts = new HashMap<>();

        for (Product product : products) {
            if (cacheProducts.containsKey(product.getId())) {
                Product cacheProduct = cacheProducts.get(product.getId());
                cacheProduct.setAmount(cacheProduct.getAmount() + product.getAmount());
            } else {
                cacheProducts.put(product.getId(), product);
            }
        }

        for (Map.Entry<Long, Product> entry : cacheProducts.entrySet()) {
            resultList.add(entry.getValue());
        }

        return resultList;
    }

    public List<Product> processAndCheckProducts(List<Product> products) {
        return productService.checkStock(processProducts(products));
    }

    public double calculateTotal(List<Product> products) {
        double total = 0.0;

        for (Product product : products) {
            total += product.getAmount() * product.getPrice();
        }

        return total;
    }

    public Order create(Order order) {
        order.setProducts(processAndCheckProducts(order.getProducts()));
        order.setTotal(calculateTotal(order.getProducts()));
        order.setStatus(OrderStatus.CART);

        return orderRepository.save(order);
    }

    public Order findById(String id) {
        return orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
    }

    public Order findByIdAndCancel(String id) {
        Order order = findById(id);

        if (!OrderStatus.CART.equals(order.getStatus())) {
            throw new OrderCannotBeChangedException();
        }

        order.setStatus(OrderStatus.CANCELED);

        return orderRepository.save(order);
    }

    public Order findByIdAndUpdate(String id, Order order) {
        Order dbOrder = findById(id);

        if (!OrderStatus.CART.equals(dbOrder.getStatus())) {
            throw new OrderCannotBeChangedException();
        }

        if (Objects.nonNull(order.getProducts())) {
            dbOrder.setProducts(processAndCheckProducts(order.getProducts()));
            dbOrder.setTotal(calculateTotal(dbOrder.getProducts()));
        }

        if (Objects.nonNull(order.getUser())) {
            dbOrder.setUser(order.getUser());
        }

        return orderRepository.save(dbOrder);
    }

    public void findByIdAndUpdateStatus(String id, OrderStatus status) {
        Order dbOrder = findById(id);
        
        dbOrder.setStatus(status);

        orderRepository.save(dbOrder);
    }

    public Order findByIdAndCommit(String id) {
        Order dbOrder = findById(id);
        
        if (!OrderStatus.CART.equals(dbOrder.getStatus())) {
            throw new OrderCannotBeChangedException();
        }

        if (Objects.isNull(dbOrder.getUser())) {
            throw new OrderHasNoUserException();
        }

        productService.batchUpdate(dbOrder.getProducts());
        dbOrder.setStatus(OrderStatus.PENDING);
        
        template.convertAndSend(RabbitMQConfig.ORDERS_PAYMENTS_DIRECT_EXCHANGE, "", dbOrder);

        return orderRepository.save(dbOrder);
    }

    public List<Order> listByUserId(String id) {
        if (id.equals("")) {
            throw new RequestHasNoUserIdException();
        }
        return orderRepository.listByUserId(id);
    }
}
