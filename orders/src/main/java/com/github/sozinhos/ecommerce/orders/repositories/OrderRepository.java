package com.github.sozinhos.ecommerce.orders.repositories;

import com.github.sozinhos.ecommerce.orders.entities.Order;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
}
