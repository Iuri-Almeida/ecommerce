package com.github.sozinhos.ecommerce.users.repositories;

import com.github.sozinhos.ecommerce.users.entities.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {
}
