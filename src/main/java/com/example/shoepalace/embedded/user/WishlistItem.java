package com.example.shoepalace.embedded.user;

import lombok.Data;

import java.time.Instant;

@Data
public class WishlistItem {
    private String productId;        // reference to Product._id
    private Instant addedAt = Instant.now();
}
