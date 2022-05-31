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
    public TopicExchange ordersExchange() {
        return new TopicExchange("orders");
    }

    @Bean
    public Queue ordersPendingQueue() {
        return new Queue("orders.pending");
    }

    @Bean
    public Binding ordersPendingBinding(
            Queue ordersPendingQueue,
            TopicExchange ordersExchange
    ) {
        return BindingBuilder
                .bind(ordersPendingQueue)
                .to(ordersExchange)
                .with("orders.pending");
    }

    @Bean
    public TopicExchange paymentsExchange() {
        return new TopicExchange("payments");
    }

    @Bean
    public Queue paymentsSuccessQueue() {
        return new Queue("payments.success");
    }

    @Bean
    public Binding paymentsSuccessBinding(
            Queue paymentsSuccessQueue,
            TopicExchange paymentsExchange
    ) {
        return BindingBuilder
                .bind(paymentsSuccessQueue)
                .to(paymentsExchange)
                .with("payments.success");
    }

    @Bean
    public Queue paymentsErrorQueue() {
        return new Queue("payments.error");
    }

    @Bean
    public Binding paymentsErrorBinding(
            Queue paymentsErrorQueue,
            TopicExchange paymentsExchange
    ) {
        return BindingBuilder
                .bind(paymentsErrorQueue)
                .to(paymentsExchange)
                .with("payments.error");
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
