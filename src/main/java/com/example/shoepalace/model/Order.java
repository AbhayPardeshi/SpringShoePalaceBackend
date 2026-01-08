package com.example.shoepalace.model;

import com.example.shoepalace.embedded.order.OrderItem;
import com.example.shoepalace.embedded.user.Address;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "orders")
public class Order {

    @Id
    private String orderId;
    private String userId;

    // CREATED -> PAID -> SHIPPED -> DELIVERED
    //              ↓
    //          CANCELLED

    private OrderStatus orderStatus;
    private List<OrderItem> orderItemList;
    private BigDecimal subTotal;
    private BigDecimal tax;
    private BigDecimal shippingCharge;
    private BigDecimal finalAmount;
    private Address shippingAddress;
    private String paymentMethod;

    @CreatedDate
    private Instant createdAt;



}
