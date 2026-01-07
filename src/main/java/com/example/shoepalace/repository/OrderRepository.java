package com.example.shoepalace.repository;

import com.example.shoepalace.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order,String> {
}
