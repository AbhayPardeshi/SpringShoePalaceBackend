package com.example.shoepalace.requestDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OrderCheckoutRequest {

    @NotBlank(message = "Address ID is required")
    private String addressId;

    @NotBlank(message = "Payment method is required")
    private String paymentMethod;
}
