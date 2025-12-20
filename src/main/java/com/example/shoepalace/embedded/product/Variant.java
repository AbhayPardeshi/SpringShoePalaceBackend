package com.example.shoepalace.embedded.product;

import lombok.Data;

@Data
public class Variant {
    private String size;           // 6,7,8,9,10
    private String color;          // Black, White
    private int stockQuantity;
    private boolean isAvailable = true;
}
