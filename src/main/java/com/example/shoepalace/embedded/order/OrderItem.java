package com.example.shoepalace.embedded.order;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItem {
    private String productId;
    private String productName;
    private String selectedSize;
    private String selectedColor;
    private int quantity;
    private BigDecimal priceAtOrderTime;
}

