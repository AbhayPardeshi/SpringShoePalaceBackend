package com.example.shoepalace.embedded.user;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Wishlist {
    private List<WishlistItem> products = new ArrayList<>();
    private int quantity = 0;
}
