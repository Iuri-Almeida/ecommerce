package com.github.sozinhos.ecommerce.payments.config;

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
        return new Queue("orders.payments");
    }

    @Bean
    public DirectExchange paymentsOrdersExchange() {
        return new DirectExchange("payments.orders");
    }

    @Bean
    public FanoutExchange paymentsOrdersAndProductsExchange() {
        return new FanoutExchange("payments.orders_products");
    }

    @Bean
    public Queue paymentsOrdersQueue() {
        return new Queue("payments.orders");
    }

    @Bean
    public Queue paymentsProductsQueue() {
        return new Queue("payments.products");
    }

    @Bean
    public Binding paymentsOrdersBinding(
            Queue paymentsOrdersQueue,
            DirectExchange paymentsOrdersExchange
    ) {
        return BindingBuilder
                .bind(paymentsOrdersQueue)
                .to(paymentsOrdersExchange)
                .with("");
    }

    @Bean
    public Binding paymentsOrdersFanoutBinding(
            Queue paymentsOrdersQueue,
            FanoutExchange paymentsOrdersAndProductsExchange
    ) {
        return BindingBuilder
                .bind(paymentsOrdersQueue)
                .to(paymentsOrdersAndProductsExchange);
    }

    @Bean
    public Binding paymentsProductsFanoutBinding(
            Queue paymentsProductsQueue,
            FanoutExchange paymentsOrdersAndProductsExchange
    ) {
        return BindingBuilder
                .bind(paymentsProductsQueue)
                .to(paymentsOrdersAndProductsExchange);
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
