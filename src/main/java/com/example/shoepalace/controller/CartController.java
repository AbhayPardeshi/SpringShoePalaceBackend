package com.example.shoepalace.controller;

import com.example.shoepalace.requestDTO.AddToCartRequest;
import com.example.shoepalace.responseDTO.CartItemResponseDTO;
import com.example.shoepalace.services.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/api/cart")
public class CartController {

    private CartService cartService;

    CartController (CartService cartService){
        this.cartService = cartService;
    }

    @PostMapping()
    public ResponseEntity<?> addToCart(@RequestBody AddToCartRequest addToCartRequest, Authentication authentication) {

        // option 1 - to get the authenticated user
        // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         String userEmail = authentication.getName();

         try{
             cartService.addToCart(addToCartRequest,userEmail);
         } catch (Exception e) {
             throw new RuntimeException(e);
         }

         return ResponseEntity.ok().body("Added Successfully");
    }
}
