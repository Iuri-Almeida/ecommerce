package com.github.sozinhos.ecommerce.orders.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String ORDERS_PAYMENTS_QUEUE = "orders.payments";
    public static final String PAYMENTS_ORDERS_QUEUE = "payments.orders";
    public static final String ORDERS_PAYMENTS_DIRECT_EXCHANGE = "ordersPaymentsDirect";

    @Value("${spring.rabbitmq.host}")
    String host;

    @Value("${spring.rabbitmq.port}")
    int port;

    @Value("${spring.rabbitmq.username}")
    String username;

    @Value("${spring.rabbitmq.password}")
    String password;

    @Bean
    public Queue ordersPaymentsQueue() {
        return new Queue(ORDERS_PAYMENTS_QUEUE);
    }

    @Bean
    public Queue paymentsOrdersQueue() {
        return new Queue(PAYMENTS_ORDERS_QUEUE);
    }

    @Bean
    public DirectExchange ordersPaymentsDirectExchange() {
        return new DirectExchange(ORDERS_PAYMENTS_DIRECT_EXCHANGE);
    }

    @Bean
    public Binding ordersPaymentsDirectBinding(
        Queue ordersPaymentsQueue,
        DirectExchange ordersPaymentsDirectExchange
    ) {
        return BindingBuilder
            .bind(ordersPaymentsQueue)
            .to(ordersPaymentsDirectExchange)
            .with("");
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setHost(host);
        cachingConnectionFactory.setPort(port);
        cachingConnectionFactory.setUsername(username);
        cachingConnectionFactory.setPassword(password);
        return cachingConnectionFactory;
    }

    @Bean
    public RabbitTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(this.messageConverter());
        return template;
    }
}
