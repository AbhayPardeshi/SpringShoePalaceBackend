package com.example.shoepalace.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "products")
public class Product {
//    @Id
//    private Integer id;                 // _id in MongoDB
//
//    private String name;
//    private String make;
//    private String imageUrl;
//    private String description;
//    private String originalPrice;
//    private String discountedPrice;
//    private String rating;
//    private String totalRatings;
//    private String productColor;
//
//    private Boolean isAvailable = true;
//    private Boolean isWishlisted = false;
//    private Boolean isAddedToCart = false;
//    private Boolean isFastDelivery = false;
//    private Boolean isLatest = false;
//
//    private List<String> availableSize = List.of("XS", "S", "L", "XL", "XXL", "3XL");
//
//    private String categoryName;
//
//    private Integer quantitiesInCart = 0;   // Default = 0
//
//    private Integer count = 10;             // Default = 10

    @Id
    private String id;  // MongoDB ObjectId auto-generated

    private String name;
    private String brand;
    private Integer price;
    private String category;
    private String imageUrl;
    private Double rating;
    private Integer stock;
    private String[] sizes;
}
