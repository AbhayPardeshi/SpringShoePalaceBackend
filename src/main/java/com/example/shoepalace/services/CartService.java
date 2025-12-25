package com.example.shoepalace.services;

import com.example.shoepalace.embedded.product.Pricing;
import com.example.shoepalace.embedded.user.Cart;
import com.example.shoepalace.embedded.user.CartItem;
import com.example.shoepalace.exception.EmailNotFoundException;
import com.example.shoepalace.exception.InvalidCartOperationException;
import com.example.shoepalace.exception.ProductNotFoundException;
import com.example.shoepalace.model.Product;
import com.example.shoepalace.model.User;
import com.example.shoepalace.repository.ProductRepository;
import com.example.shoepalace.repository.UserRepository;
import com.example.shoepalace.requestDTO.AddToCartRequest;

import java.math.BigDecimal;
import java.util.Optional;

public class CartService {

    UserRepository userRepository;
     ProductRepository productRepository;

    CartService(UserRepository userRepository, ProductRepository productRepository){
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public void addToCart(AddToCartRequest addToCartRequest, String userEmail) throws Exception {

        User savedUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EmailNotFoundException("no user found"));


        if (addToCartRequest.getQuantity() < 0) {
            throw new InvalidCartOperationException("quantity should be greater than 0");
        }

        Cart userCart = savedUser.getCart();
        if (userCart == null) {
            userCart = new Cart();
            savedUser.setCart(userCart);
        }

        Product product = productRepository.findProductById(addToCartRequest.getProductId())
                .orElseThrow(()-> new ProductNotFoundException("no product found"));


        CartItem existingItem = null;

        for (CartItem item : userCart.getCartItemList()) {
            if (item.getProductId().equals(addToCartRequest.getProductId())
                    && item.getSelectedSize().equals(addToCartRequest.getSelectedSize())
                    && item.getSelectedColor().equals(addToCartRequest.getSelectedColor())) {

                existingItem = item;
                break;
            }
        }

        if(existingItem != null){
            existingItem.setQuantity(
                    existingItem.getQuantity() + addToCartRequest.getQuantity()
            );
        }else{
            CartItem newItem = getCartItem(addToCartRequest, product);
            userCart.getCartItemList().add(newItem);
        }

        userRepository.save(savedUser);
    }

    private static CartItem getCartItem(AddToCartRequest addToCartRequest, Product product) throws Exception {

        // get the price for the product
        Pricing pricing = product.getProductPricing();
        if (pricing == null || pricing.getDiscountedPrice() == null) {
            throw new InvalidCartOperationException("Pricing not available for product");
        }

        CartItem newItem = new CartItem();

        newItem.setProductId(addToCartRequest.getProductId());
        newItem.setSelectedSize(addToCartRequest.getSelectedSize());
        newItem.setSelectedColor(addToCartRequest.getSelectedColor());
        newItem.setQuantity(addToCartRequest.getQuantity());
        newItem.setPriceAtAddTime(pricing.getDiscountedPrice());

        return newItem;
    }
}
