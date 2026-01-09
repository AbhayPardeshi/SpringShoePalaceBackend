package com.example.shoepalace.controller;

import com.example.shoepalace.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/orders")
public class OrderAdminController {

    private final OrderService orderService;

    public OrderAdminController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/{orderId}/ship")
    public ResponseEntity<?> shipOrder(@PathVariable String orderId, Authentication auth) {
        orderService.shipOrder(orderId, auth.getName());
        return ResponseEntity.ok("Order marked as SHIPPED");
    }

    @PostMapping("/{orderId}/deliver")
    public ResponseEntity<?> deliverOrder(@PathVariable String orderId, Authentication auth) {
        orderService.deliverOrder(orderId, auth.getName());
        return ResponseEntity.ok("Order marked as DELIVERED");
    }

}
