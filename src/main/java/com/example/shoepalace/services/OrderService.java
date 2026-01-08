package com.example.shoepalace.services;

import com.example.shoepalace.embedded.order.OrderItem;
import com.example.shoepalace.embedded.user.Cart;
import com.example.shoepalace.embedded.user.CartItem;
import com.example.shoepalace.exception.InvalidOrderOperationException;
import com.example.shoepalace.exception.OrderNotFoundException;
import com.example.shoepalace.exception.ProductNotFoundException;
import com.example.shoepalace.mapper.OrderMapper;
import com.example.shoepalace.model.Order;
import com.example.shoepalace.model.OrderStatus;
import com.example.shoepalace.model.Product;
import com.example.shoepalace.model.User;
import com.example.shoepalace.repository.OrderRepository;
import com.example.shoepalace.responseDTO.OrderResponseDTO;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    UserService userService;
    ProductService productService;
    CartService cartService;
    OrderRepository orderRepository;
    OrderMapper orderMapper;

    public OrderService(UserService userService,
                        ProductService productService,
                        CartService cartService,
                        OrderRepository orderRepository,
                        OrderMapper orderMapper) {
        this.userService = userService;
        this.productService = productService;
        this.cartService = cartService;
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    public List<Order> getAllOrders(String userEmail){
        User savedUser = userService.getCurrentUser(userEmail);

        String userId = savedUser.getId();

        return orderRepository.findByUserId(userId);
    }

    public OrderResponseDTO getOrderDetails(String orderId, String userEmail){
        User user = userService.getCurrentUser(userEmail);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("No order found"));

        if (!order.getUserId().equals(user.getId())) {
            throw new AccessDeniedException("This order does not belong to you");
        }

        return orderMapper.toOrderResponse(order);
    }

    public String createOrder(String userEmail){
        User savedUser = userService.getCurrentUser(userEmail);

        Cart userCart = savedUser.getCart();

        if(userCart == null || userCart.getCartItemList().isEmpty()){
            throw new InvalidOrderOperationException("Your cart is empty, add products to proceed!");
        }

        Order newOrder = new Order();

        List<CartItem> cartItemList = userCart.getCartItemList();
        List<OrderItem> orderItemList = new ArrayList<>();

        BigDecimal totalValue = BigDecimal.ZERO;
        for(CartItem cartItem : cartItemList){
            OrderItem newItem = new OrderItem();

            Product product = productService.getById(cartItem.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException("product not found"));

            newItem.setProductId(product.getId());
            newItem.setProductName(product.getName());
            newItem.setSelectedSize(cartItem.getSelectedSize());
            newItem.setSelectedColor(cartItem.getSelectedColor());
            newItem.setQuantity(cartItem.getQuantity());
            newItem.setPriceAtOrderTime(cartItem.getPriceAtAddTime());

            totalValue = totalValue.add(newItem.getPriceAtOrderTime().multiply(BigDecimal.valueOf(newItem.getQuantity())));

            orderItemList.add(newItem);
        }

        BigDecimal taxAmount = totalValue.multiply(new BigDecimal("0.18"));
        BigDecimal shippingCharge = BigDecimal.valueOf(200);
        BigDecimal totalAmount = totalValue.add(taxAmount).add(shippingCharge);

        newOrder.setUserId(savedUser.getId());
        newOrder.setOrderStatus(OrderStatus.CREATED);
        newOrder.setOrderItemList(orderItemList);
        newOrder.setSubTotal(totalValue);

        newOrder.setTax(taxAmount);
        newOrder.setShippingCharge(shippingCharge);
        newOrder.setFinalAmount(totalAmount);

        orderRepository.save(newOrder);
        cartService.clearCart(userEmail);

        return newOrder.getOrderId();
    }
}
