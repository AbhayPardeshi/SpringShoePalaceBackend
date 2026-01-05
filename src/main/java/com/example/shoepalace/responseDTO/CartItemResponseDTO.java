package com.example.shoepalace.responseDTO;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CartItemResponseDTO {

    private String cartItemId;
    private String productId;
    private String size;
    private String color;
    private int quantity;
    private BigDecimal price;
}
