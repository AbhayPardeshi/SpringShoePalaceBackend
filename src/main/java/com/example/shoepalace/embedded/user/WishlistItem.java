package com.example.shoepalace.embedded.user;

import java.time.Instant;

public class WishlistItem {
    private String productId;        // reference to Product._id
    private Instant addedAt = Instant.now();
}
