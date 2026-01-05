package com.example.shoepalace.mapper;

import com.example.shoepalace.embedded.user.Cart;
import com.example.shoepalace.embedded.user.CartItem;
import com.example.shoepalace.responseDTO.CartItemResponseDTO;
import com.example.shoepalace.responseDTO.CartResponseDTO;
import com.example.shoepalace.responseDTO.CheckoutPreviewResponseDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class CartMapper {

    public CartResponseDTO toResponse(Cart cart) {
        CartResponseDTO response = new CartResponseDTO();

        List<CartItemResponseDTO> items = cart.getCartItemList()
                .stream()
                .map(this::toItemResponse)
                .toList();

        response.setItems(items);
        response.setTotalAmount(calculateTotal(items));

        return response;
    }

    public CheckoutPreviewResponseDTO toCheckoutPreview(Cart cart){
        CheckoutPreviewResponseDTO responseDTO = new CheckoutPreviewResponseDTO();

        List<CartItemResponseDTO> items = cart.getCartItemList()
                .stream()
                .map(this::toItemResponse)
                .toList();

        responseDTO.setItems(items);
        responseDTO.setSubTotal(calculateTotal(items));

        return responseDTO;
    }

    private CartItemResponseDTO toItemResponse(CartItem item) {
        CartItemResponseDTO dto = new CartItemResponseDTO();
        dto.setCartItemId(item.getCartItemId());
        dto.setProductId(item.getProductId());
        dto.setSize(item.getSelectedSize());
        dto.setColor(item.getSelectedColor());
        dto.setQuantity(item.getQuantity());
        dto.setPrice(item.getPriceAtAddTime());
        return dto;
    }

    private BigDecimal calculateTotal(List<CartItemResponseDTO> items) {
        return items.stream()
                .map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

