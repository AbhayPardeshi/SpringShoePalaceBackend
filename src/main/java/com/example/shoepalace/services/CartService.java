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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Service
public class CartService {

    UserRepository userRepository;
     ProductRepository productRepository;

    CartService(UserRepository userRepository, ProductRepository productRepository){
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public Cart viewCart(String userEmail){
        User savedUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EmailNotFoundException("no user found"));

        return savedUser.getCart() != null
                ? savedUser.getCart()
                : new Cart();
    }

    public void clearCart(String userEmail){
        User savedUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EmailNotFoundException("no user found"));

        Cart userCart = savedUser.getCart();

        if (userCart == null || userCart.getCartItemList() == null) {
            return;
        }

        userCart.getCartItemList().clear();
        userRepository.save(savedUser);

    }


    public void addToCart(AddToCartRequest addToCartRequest, String userEmail) {

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

    public void removeFromCart(String cartItemId, String userEmail){
        User savedUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EmailNotFoundException("no user found"));

        Cart userCart = savedUser.getCart();

        List<CartItem> cartItems = userCart.getCartItemList();

        Iterator<CartItem> iterator = cartItems.iterator();

        boolean removed = false;

        while (iterator.hasNext()) {
            if (iterator.next().getCartItemId().equals(cartItemId)) {
                iterator.remove();
                removed = true;
                break;
            }
        }

        if (!removed) {
            throw new InvalidCartOperationException("Cart item not found");
        }


        userRepository.save(savedUser);

    }

    public void updateQuantity(String cartItemId, String userEmail, int quantity){
        User savedUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EmailNotFoundException("no user found"));

        if (quantity <= 0) {
            throw new InvalidCartOperationException("Quantity must be greater than 0");
        }

        Cart userCart = savedUser.getCart();

        List<CartItem> cartItems = userCart.getCartItemList();

        Iterator<CartItem> iterator = cartItems.iterator();

        boolean updated = false;

        while (iterator.hasNext()) {
            CartItem item = iterator.next();
            System.out.println(item.getCartItemId());
            if (item.getCartItemId().equals(cartItemId)) {
                item.setQuantity(quantity);
                updated = true;
                break;
            }
        }

        if (!updated) {
            throw new InvalidCartOperationException("Cart item not found");
        }


        userRepository.save(savedUser);
    }

    private static CartItem getCartItem(AddToCartRequest addToCartRequest, Product product) {

        // get the price for the product
        Pricing pricing = product.getProductPricing();
        if (pricing == null || pricing.getDiscountedPrice() == null) {
            throw new InvalidCartOperationException("Pricing not available for product");
        }

        CartItem newItem = new CartItem();

        newItem.setCartItemId(UUID.randomUUID().toString());
        newItem.setProductId(addToCartRequest.getProductId());
        newItem.setSelectedSize(addToCartRequest.getSelectedSize());
        newItem.setSelectedColor(addToCartRequest.getSelectedColor());
        newItem.setQuantity(addToCartRequest.getQuantity());
        newItem.setPriceAtAddTime(pricing.getDiscountedPrice());

        return newItem;
    }


}
