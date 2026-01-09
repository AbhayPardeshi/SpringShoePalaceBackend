package com.example.shoepalace.repository;

import com.example.shoepalace.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order,String> {
    List<Order> findByUserId(String userId);

}
