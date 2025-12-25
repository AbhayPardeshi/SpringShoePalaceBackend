package com.example.shoepalace.requestDTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddToCartRequest {
    @NotBlank(message = "Product id is required")
    private String productId;

    @NotBlank(message = "Size is required")
    private String selectedSize;

    // Optional if product has only one color
    private String selectedColor;

    @NotNull
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

}
