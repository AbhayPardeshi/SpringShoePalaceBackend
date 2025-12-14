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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Document(collection = "users")
@Data
public class User implements UserDetails {
    @Id
    private String id;

    private String userName;

    @Email
    @Indexed(unique = true)
    @NotNull(message = "email cannot be blank")
    private String email;


    private String passwordHash;

    private String cartId;
    private String wishlistId;

    private List<Address> userAddressList = new ArrayList<>();

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    private List<String> roles = List.of("USER");
    private boolean isVerified = false;
    private boolean isActive = true;
    private int failedLoginAttempts = 0;

    private Instant lockedUntil;
    private Instant lastLoginAt;

    @CreatedDate
    private Instant lastPasswordChangeAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword(){
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return "";
    }

}
