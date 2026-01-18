package com.example.shoepalace.repository.impl;

import com.example.shoepalace.model.Product;
import com.example.shoepalace.repository.ProductRepositoryCustom;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
        ObjectId objectId = new ObjectId(id);
        return Optional.ofNullable(mongoTemplate.findById(objectId, Product.class));
    }

    @Override
    public Product updateProduct(Product product) {

        mongoTemplate.getDb().listCollectionNames().forEach(
                name -> System.out.println("Collection available: " + name)
        );

        if (product.getId() == null) {
            throw new IllegalArgumentException("Product ID cannot be null for update");
        }

        ObjectId objectId = new ObjectId(product.getId());

        // Replace existing document by ID
        Query query = new Query(Criteria.where("_id").is(objectId));

        // Overwrite entire document
        mongoTemplate.findAndReplace(query, product);

        return product;
    }

    @Override
    public boolean deleteProduct(String id) {

        System.out.println("----------------------");
        System.out.println("Deleting from DB: " + mongoTemplate.getDb().getName());
        mongoTemplate.getDb().listCollectionNames().forEach(
                name -> System.out.println("Collection available: " + name)
        );
        System.out.println("----------------------");

        ObjectId objectId = new ObjectId(id);

        Query query = new Query(Criteria.where("_id").is(objectId));

        var result = mongoTemplate.remove(query, Product.class);

        return result.getDeletedCount() > 0;
    }



}
