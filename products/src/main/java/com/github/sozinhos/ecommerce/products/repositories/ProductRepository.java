package com.github.sozinhos.ecommerce.products.repositories;

import com.github.sozinhos.ecommerce.products.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
