package com.example.shoepalace.controller;

import com.example.shoepalace.embedded.user.Wishlist;
import com.example.shoepalace.requestDTO.AddToWishlistRequest;
import com.example.shoepalace.services.WishlistService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService){
        this.wishlistService = wishlistService;
    }

    @GetMapping
    public ResponseEntity<?> viewWishlist(Authentication authentication){
        String userEmail = authentication.getName();
        Wishlist userWishlist = wishlistService.viewWishlist(userEmail);

        return ResponseEntity.ok().body(userWishlist);
    }

    @DeleteMapping
    public ResponseEntity<?> clearWishlist(Authentication authentication){
        String userEmail = authentication.getName();
        wishlistService.clearWishlist(userEmail);

        return ResponseEntity.ok("Wishlist cleared successfully");
    }

    @PostMapping
    public ResponseEntity<?> addToWishlist(@Valid @RequestBody AddToWishlistRequest addToWishlistRequest,
                                           Authentication authentication){
        String userEmail = authentication.getName();

        wishlistService.addToWishlist(addToWishlistRequest,userEmail);

        return ResponseEntity.ok().body("Item added to wishlist successfully");
    }

    @DeleteMapping(path = "/items/{productId}")
    public ResponseEntity<?> removeFromWishlist(@PathVariable String productId,
                                                Authentication authentication){
        String userEmail = authentication.getName();

        wishlistService.removeFromWishList(productId,userEmail);

        return ResponseEntity.ok().body("Item removed from wishlist successfully");
    }

}
