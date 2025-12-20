package com.example.shoepalace.embedded.user;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class Cart {

    private List<CartItem> cartItemList = new ArrayList<>();
    public int getTotalItems() {
        return cartItemList.stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }
}
