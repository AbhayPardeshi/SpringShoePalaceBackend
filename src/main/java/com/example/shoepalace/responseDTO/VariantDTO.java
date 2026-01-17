package com.example.shoepalace.responseDTO;

import lombok.Data;

@Data
public class VariantDTO {
    private String size;
    private String color;
    private int stockQuantity;
    private boolean isAvailable;
}

