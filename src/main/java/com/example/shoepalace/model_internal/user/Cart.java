package com.example.shoepalace.model_internal.user;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class Cart {

    private List<CartItem> cartItemList = new ArrayList<>();
    private int itemsInCart = 0;
}
