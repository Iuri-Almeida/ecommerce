package com.github.sozinhos.ecommerce.orders.repositories;

import com.github.sozinhos.ecommerce.orders.entities.Order;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
    @Query("{ 'user.id' : ?0 }")
    List<Order> listByUserId(String id);
}
