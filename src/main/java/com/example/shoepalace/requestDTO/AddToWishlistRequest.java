package com.example.shoepalace.requestDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddToWishlistRequest {
    @NotBlank(message = "Product id is required")
    private String productId;

}
