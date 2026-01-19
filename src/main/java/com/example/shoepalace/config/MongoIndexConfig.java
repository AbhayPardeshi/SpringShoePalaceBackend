package com.example.shoepalace.config;

import org.springframework.context.event.EventListener;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexOperations;

@Configuration
public class MongoIndexConfig {

    private final MongoTemplate mongoTemplate;

    public MongoIndexConfig(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initIndexes() {

        IndexOperations indexOps = mongoTemplate.indexOps("products");

        indexOps.createIndex(new Index().on("brand",          Sort.Direction.ASC));
        indexOps.createIndex(new Index().on("categories",     Sort.Direction.ASC));
        indexOps.createIndex(new Index().on("gender",         Sort.Direction.ASC));
        indexOps.createIndex(new Index().on("productPricing.discountedPrice", Sort.Direction.ASC));
        indexOps.createIndex(new Index().on("createdAt",      Sort.Direction.DESC));
        indexOps.createIndex(new Index().on("varients.variantList.size",  Sort.Direction.ASC));
        indexOps.createIndex(new Index().on("varients.variantList.color", Sort.Direction.ASC));

        // COMPOUND INDEX
        indexOps.createIndex(
                new Index()
                        .on("categories", Sort.Direction.ASC)
                        .on("gender",     Sort.Direction.ASC)
                        .on("productPricing.discountedPrice", Sort.Direction.ASC)
                        .on("createdAt", Sort.Direction.DESC)
        );
    }
}
