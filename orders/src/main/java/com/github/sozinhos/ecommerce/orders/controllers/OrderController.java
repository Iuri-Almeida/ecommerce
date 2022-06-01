package com.github.sozinhos.ecommerce.orders.controllers;

import com.github.sozinhos.ecommerce.orders.entities.Order;
import com.github.sozinhos.ecommerce.orders.services.OrderService;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.create(order);
    }

    @GetMapping
    public List<Order> listOrders(@RequestParam(defaultValue = "", value = "user_id") String userId) {
        return orderService.listByUserId(userId);
    }

    @GetMapping("/{id}")
    public Order retrieveOrder(@PathVariable String id) {
        return orderService.findById(id);
    }

    @PatchMapping("/{id}")
    public Order updateOrder(@PathVariable String id, @RequestBody Order order) {
        return orderService.findByIdAndUpdate(id, order);
    }

    @DeleteMapping("/{id}")
    public Order deleteOrder(@PathVariable String id) {
        return orderService.findByIdAndCancel(id);
    }

    @PostMapping("/{id}/commit")
    public Order orderCommit(@PathVariable String id) {
        return orderService.findByIdAndCommit(id);
    }
}
