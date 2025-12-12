package com.example.shoepalace.repository;

import com.example.shoepalace.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {
    boolean existsByEmail(String email);
}
