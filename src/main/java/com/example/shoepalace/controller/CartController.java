package com.example.shoepalace.controller;

import com.example.shoepalace.embedded.user.Cart;
import com.example.shoepalace.mapper.CartMapper;
import com.example.shoepalace.requestDTO.AddToCartRequest;
import com.example.shoepalace.requestDTO.UpdateCartItemRequest;
import com.example.shoepalace.services.CartService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/cart")
public class CartController {

    private final CartService cartService;
    private final CartMapper cartMapper;

    public CartController (CartService cartService, CartMapper cartMapper){
        this.cartService = cartService;
        this.cartMapper = cartMapper;
    }

    @GetMapping
    public ResponseEntity<?> viewCart(Authentication authentication){
        Cart cart = cartService.viewCart(authentication.getName());
        return ResponseEntity.ok(cartMapper.toResponse(cart));
    }

    @DeleteMapping
    public ResponseEntity<?> clearCart(Authentication authentication){
        cartService.clearCart(authentication.getName());
        return ResponseEntity.ok("Cart cleared successfully");

    }

//    ToDo - when we checkout do required validation to check correct price, stock, tax
//            before checking out the items
//            -- helps in creating the order details page
//            -> create a CheckoutPreviewService, CheckoutContext - holding the details
//            -> implemet the toCheckoutPreview() in cartMapper

//    @GetMapping
//    public ResponseEntity<?> checkoutPreview(Authentication authentication){
//        CheckoutContext context =
//                checkoutPreviewService.preview(authentication.getName());
//        return ResponseEntity.ok(cartMapper.toCheckoutPreview(context));
//    }

    @PostMapping
    public ResponseEntity<?> addToCart(@Valid @RequestBody AddToCartRequest addToCartRequest,
                                       Authentication authentication) {

        // option 1 - to get the authenticated user
        // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         String userEmail = authentication.getName();

         cartService.addToCart(addToCartRequest,userEmail);

         return ResponseEntity.ok().body("Item added to cart successfully");
    }

    @DeleteMapping(path = "/items/{cartItemId}")
    public ResponseEntity<?> removeFromCart(@PathVariable String cartItemId,
                                            Authentication authentication){

        String userEmail = authentication.getName();

        cartService.removeFromCart(cartItemId,userEmail);

        return ResponseEntity.ok().body("Item removed from cart successfully");
    }

    @PutMapping(path = "/items/{cartItemId}")
    public ResponseEntity<?> updateQuantity(@PathVariable String cartItemId,
                                            @Valid @RequestBody UpdateCartItemRequest request,
                                            Authentication authentication){
        String userEmail = authentication.getName();

        cartService.updateQuantity(cartItemId,userEmail,request.getQuantity());

        return ResponseEntity.ok().body("Quantity updated in cart successfully");
    }
}
