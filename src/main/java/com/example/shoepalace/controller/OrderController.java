package com.example.shoepalace.controller;

import com.example.shoepalace.mapper.OrderMapper;
import com.example.shoepalace.model.Order;
import com.example.shoepalace.requestDTO.OrderCheckoutRequest;
import com.example.shoepalace.responseDTO.OrderResponseDTO;
import com.example.shoepalace.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    public OrderController(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @GetMapping
    public ResponseEntity<?> getAllOrders(Authentication authentication){
        List<Order> orderList = orderService.getAllOrders(authentication.getName());

        return ResponseEntity.ok(orderMapper.toOrderResponseList(orderList));
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<?> cancelOrder(Authentication authentication,
                                         @PathVariable String orderId){
            orderService.deleteOrder(orderId,authentication.getName());
        return ResponseEntity.ok("Order deleted successfully");
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrder(@PathVariable String orderId,
                                      Authentication auth) {
        OrderResponseDTO response = orderService.getOrderDetails(orderId, auth.getName());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> createOrder(Authentication authentication) {
        String orderId = orderService.createOrder(authentication.getName());
        return ResponseEntity.ok(orderId);
    }

    @PutMapping("/{orderId}/checkout")
    public ResponseEntity<?> updateCheckoutInfo(Authentication authentication,
                                                OrderCheckoutRequest request,
                                                @PathVariable String orderId){
        orderService.updateCheckoutInfo(orderId, authentication.getName(), request);

        return ResponseEntity.ok("Checkout details updated");
    }

    @PostMapping("/{orderId}/pay")
    public ResponseEntity<?> payOrder(@PathVariable String orderId,
                                      Authentication auth) {
        orderService.payOrder(orderId, auth.getName());
        return ResponseEntity.ok("Order marked as PAID");
    }


}
