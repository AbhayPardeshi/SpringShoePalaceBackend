package com.example.shoepalace.repository;

import com.example.shoepalace.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product,String>, ProductRepositoryCustom {
}
