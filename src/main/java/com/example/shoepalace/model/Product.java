package com.example.shoepalace.model;

import com.example.shoepalace.embedded.product.Pricing;
import com.example.shoepalace.embedded.product.RatingSummary;
import com.example.shoepalace.embedded.product.Varients;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "products")
public class Product {
    @Id
    private String id;  // MongoDB ObjectId auto-generated

    private String name;
    private String brand;
    private String description;
    private String category;
    private List<String> imageUrl;

    private Pricing productPricing = new Pricing();
    private RatingSummary ratingSummary = new RatingSummary();
    private Varients varients = new Varients();

    private boolean isActive = true;
    private boolean isDeleted = false;
    private boolean isInStock = true;


    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}
