package com.example.shoepalace.responseDTO;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class CartResponseDTO {

    private List<CartItemResponseDTO> items;
    private BigDecimal totalAmount;
}