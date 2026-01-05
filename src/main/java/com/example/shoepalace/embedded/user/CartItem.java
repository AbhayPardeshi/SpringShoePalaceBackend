package com.example.shoepalace.embedded.user;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Data
public class CartItem {
    private String cartItemId;
    private String productId;

    private String selectedSize;
    private String selectedColor;

    private int quantity;

    // Price locked when added to cart
    private BigDecimal priceAtAddTime;
}
