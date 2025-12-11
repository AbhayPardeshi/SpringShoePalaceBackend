package com.example.shoepalace.repository.impl;

import com.example.shoepalace.model.Product;
import com.example.shoepalace.repository.ProductRepositoryCustom;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<Product> getAll(){
        return mongoTemplate.findAll(Product.class);
    }

    @Override
    public Product createProduct(Product p){
        return mongoTemplate.save(p);
    }

    @Override
    public Optional<Product> findProductById(String id){
        System.out.println("----------------------");
        System.out.println("Saving to DB: " + mongoTemplate.getDb().getName());
        mongoTemplate.getDb().listCollectionNames().forEach(
                name -> System.out.println("Collection available: " + name)
        );
        System.out.println("----------------------");
        ObjectId objectId = new ObjectId(id);
        return Optional.ofNullable(mongoTemplate.findById(objectId, Product.class));
    }
}
