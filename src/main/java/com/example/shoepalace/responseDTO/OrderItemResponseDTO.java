package com.example.shoepalace.responseDTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemResponseDTO {
    private String productId;
    private String productName;
    private String size;
    private String color;
    private int quantity;
    private BigDecimal price;
}
