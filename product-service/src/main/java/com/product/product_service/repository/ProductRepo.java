package com.product.product_service.repository;

import com.product.product_service.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepo extends MongoRepository<Product, String> {

    Optional<Object> findByName(String name);
}


