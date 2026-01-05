package com.example.shoepalace.responseDTO;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CheckoutPreviewResponseDTO {
    private List<CartItemResponseDTO> items;
    private BigDecimal subTotal;
    private int tax;
    private BigDecimal finalAmount;
    private List<String> warnings;
}
