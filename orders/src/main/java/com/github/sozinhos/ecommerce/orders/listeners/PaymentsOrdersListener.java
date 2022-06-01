package com.github.sozinhos.ecommerce.orders.listeners;

import com.github.sozinhos.ecommerce.orders.config.RabbitMQConfig;
import com.github.sozinhos.ecommerce.orders.entities.Order;
import com.github.sozinhos.ecommerce.orders.services.OrderService;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentsOrdersListener {
    private final OrderService orderService;

    @RabbitListener(queues = RabbitMQConfig.PAYMENTS_ORDERS_QUEUE)
    public void listener(Order order) {
        orderService.findByIdAndUpdateStatus(order.getId(), order.getStatus());
    }
}
