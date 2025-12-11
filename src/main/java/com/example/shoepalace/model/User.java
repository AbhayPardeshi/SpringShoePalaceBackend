package com.example.shoepalace.model;

import com.example.shoepalace.model_internal.user.Address;
import com.example.shoepalace.model_internal.user.Cart;
import com.example.shoepalace.model_internal.user.Wishlist;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data

public class User {
    @Id
    private String id;

    private String firstName;
    private String lastName;

    @Email
    @Indexed(unique = true)
    @NotNull(message = "email cannot be blank")
    private String email;


    private String passwordHash;

    private Cart userCart = new Cart();

    private Wishlist userWishlist = new Wishlist();

    private List<Address> userAddressList = new ArrayList<>();

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;


}
