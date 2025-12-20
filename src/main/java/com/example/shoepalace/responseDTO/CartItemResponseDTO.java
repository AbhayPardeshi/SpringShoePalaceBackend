package com.example.shoepalace.responseDTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemResponseDTO {
    private String productId;
    private String productName;
    private String productImage;

    private String selectedSize;
    private String selectedColor;

    private int quantity;

    private BigDecimal pricePerUnit;   // snapshot
    private BigDecimal totalPrice;     // pricePerUnit × quantity
}
