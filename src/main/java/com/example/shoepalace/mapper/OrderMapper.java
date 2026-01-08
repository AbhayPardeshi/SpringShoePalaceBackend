package com.example.shoepalace.mapper;

import com.example.shoepalace.embedded.order.OrderItem;
import com.example.shoepalace.embedded.user.Address;
import com.example.shoepalace.model.Order;
import com.example.shoepalace.responseDTO.AddressResponseDTO;
import com.example.shoepalace.responseDTO.OrderItemResponseDTO;
import com.example.shoepalace.responseDTO.OrderResponseDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

    public List<OrderResponseDTO> toOrderResponseList(List<Order> orders) {
        return orders.stream()
                .map(this::toOrderResponse)
                .toList();
    }

    public OrderResponseDTO toOrderResponse(Order order){
        OrderResponseDTO responseDTO = new OrderResponseDTO();

        responseDTO.setOrderId(order.getOrderId());
        responseDTO.setOrderStatus(order.getOrderStatus());
        responseDTO.setOrderItemList(
                order.getOrderItemList()
                        .stream()
                        .map(this::toOrderItemResponse)
                        .toList()
        );

        // Price breakdown
        responseDTO.setSubTotal(order.getSubTotal());
        responseDTO.setTax(order.getTax());
        responseDTO.setShippingCharge(order.getShippingCharge());
        responseDTO.setFinalAmount(order.getFinalAmount());

        // Additional order details
        responseDTO.setPaymentMethod(order.getPaymentMethod());
        responseDTO.setShippingAddress(
                toAddressResponse(order.getShippingAddress())
        );
        responseDTO.setCreatedAt(order.getCreatedAt());

        return responseDTO;
    }

    private OrderItemResponseDTO toOrderItemResponse(OrderItem item) {
        OrderItemResponseDTO dto = new OrderItemResponseDTO();
        dto.setProductId(item.getProductId());
        dto.setProductName(item.getProductName());
        dto.setSize(item.getSelectedSize());
        dto.setColor(item.getSelectedColor());
        dto.setQuantity(item.getQuantity());
        dto.setPrice(item.getPriceAtOrderTime());
        return dto;
    }

    private AddressResponseDTO toAddressResponse(Address address) {
        if (address == null) {
            return null;
        }

        AddressResponseDTO dto = new AddressResponseDTO();
        dto.setId(address.getId());
        dto.setLabel(address.getLabel());
        dto.setLine1(address.getLine1());
        dto.setLine2(address.getLine2());
        dto.setCity(address.getCity());
        dto.setState(address.getState());
        dto.setPostalCode(address.getPostalCode());
        dto.setCountry(address.getCountry());
        dto.setPhoneNumber(address.getPhoneNumber());
        dto.setDefault(address.isDefault());
        return dto;
    }

}
