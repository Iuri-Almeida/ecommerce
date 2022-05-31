package com.github.sozinhos.ecommerce.payments.listeners;

import com.github.sozinhos.ecommerce.payments.entities.Order;
import com.github.sozinhos.ecommerce.payments.entities.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
public class OrdersPaymentsListener {

    private final RabbitTemplate template;

    @RabbitListener(queues = "orders.payments")
    public void listener(Order order) {
      boolean successfulPayment = new Random().nextInt(100) < 80;

      if (successfulPayment) {
          order.setStatus(OrderStatus.SUCCESS);
      } else {
          order.setStatus(OrderStatus.ERROR);
          template.convertAndSend("payments", "payments.products", order);
      }

      template.convertAndSend("payments", "payments.orders", order);
    }
}
