package com.example.shoepalace.services;

import com.example.shoepalace.embedded.user.Wishlist;
import com.example.shoepalace.embedded.user.WishlistItem;
import com.example.shoepalace.exception.EmailNotFoundException;
import com.example.shoepalace.exception.InvalidWishlistOperationException;
import com.example.shoepalace.exception.ProductNotFoundException;
import com.example.shoepalace.model.User;
import com.example.shoepalace.repository.ProductRepository;
import com.example.shoepalace.repository.UserRepository;
import com.example.shoepalace.requestDTO.AddToWishlistRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class WishlistService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final UserService userService;

    public WishlistService(UserRepository userRepository,
                           ProductRepository productRepository,
                           UserService userService) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.userService = userService;
    }

    public Wishlist viewWishlist(String userEmail){
        User savedUser = userService.getCurrentUser(userEmail);

        return savedUser.getWishlist() != null ?
                savedUser.getWishlist()
                : new Wishlist();
    }

    public void clearWishlist(String userEmail){
        User savedUser = userService.getCurrentUser(userEmail);

        Wishlist wishlistItems = savedUser.getWishlist();

        if(wishlistItems == null || wishlistItems.getProducts() == null){
            return;
        }
        wishlistItems.getProducts().clear();
        userRepository.save(savedUser);
    }

    public void addToWishlist(AddToWishlistRequest request, String userEmail) {

        User user = userService.getCurrentUser(userEmail);

        if (user.getWishlist() == null) {
            user.setWishlist(new Wishlist());
        }

        Wishlist wishlist = user.getWishlist();

        if (wishlist.getProducts() == null) {
            wishlist.setProducts(new ArrayList<>());
        }

        // validation only
        productRepository.findProductById(request.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("No product found"));

        boolean exists = wishlist.getProducts().stream()
                .anyMatch(item -> item.getProductId().equals(request.getProductId()));

        if (!exists) {
            WishlistItem item = new WishlistItem();
            item.setProductId(request.getProductId());
            item.setAddedAt(Instant.now());
            wishlist.getProducts().add(item);
        }

        userRepository.save(user);
    }
    
    public void removeFromWishList(String productId, String userEmail){
        User savedUser = userService.getCurrentUser(userEmail);


        Wishlist userWishlist = savedUser.getWishlist();

        if (userWishlist == null || userWishlist.getProducts() == null) {
            throw new InvalidWishlistOperationException("Wishlist is empty");
        }

        List<WishlistItem> wishlistItems = userWishlist.getProducts();

        Iterator<WishlistItem> itr = wishlistItems.iterator();
        boolean removed = false;

        while(itr.hasNext()){
            if(itr.next().getProductId().equals(productId)){
                itr.remove();
                removed = true;
                break;
            }
        }

        if(!removed){
            throw new InvalidWishlistOperationException("No such item found to delete");
        }

        userRepository.save(savedUser);
    }


}

