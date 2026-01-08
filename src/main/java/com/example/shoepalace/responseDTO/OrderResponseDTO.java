package com.example.shoepalace.responseDTO;


import com.example.shoepalace.embedded.user.Address;
import com.example.shoepalace.model.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
public class OrderResponseDTO {
    private String orderId;
    private OrderStatus orderStatus;
    private List<OrderItemResponseDTO> orderItemList;

    private BigDecimal subTotal;
    private BigDecimal tax;
    private BigDecimal shippingCharge;
    private BigDecimal finalAmount;

    private String paymentMethod;
    private AddressResponseDTO shippingAddress;


    private Instant createdAt;  // match model, better for serialization
}

