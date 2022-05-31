package com.github.sozinhos.ecommerce.payments.controllers;

import com.github.sozinhos.ecommerce.payments.config.OrderCanceledConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentsController {

    private final RabbitTemplate template;

    @PostMapping
    public String publish(@RequestBody String message) {
        this.template.convertAndSend(
                OrderCanceledConfig.EXCHANGE,
                OrderCanceledConfig.ROUTING_KEY,
                message
        );

        return message + " - published";
    }
}
