package com.example.shoepalace.services;

import com.example.shoepalace.config.BCryptConfig;
import com.example.shoepalace.dto.SignupRequest;
import com.example.shoepalace.model.User;
import com.example.shoepalace.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(PasswordEncoder passwordEncoder,UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public User createUser(User newUserToBeAdded){
        return userRepository.save(newUserToBeAdded);
    }

    public User signupUser(SignupRequest request){

        // hashing the plain password using Bcrypt
        String plainPassword = request.getPassword();
        String hashedPass = passwordEncoder.encode(plainPassword);

        // creating a new User
        User newUser = new User();

        // setting required details
        newUser.setUserName(request.getUserName());
        newUser.setEmail(request.getEmail().trim().toLowerCase());
        newUser.setPasswordHash(hashedPass);
        newUser.setCartId(null);
        newUser.setWishlistId(null);
        newUser.setUserAddressList(new ArrayList<>());

        newUser.setRoles(new ArrayList<>(List.of("USER")));

        // saving the user in Db

        return userRepository.save(newUser);

    }
}
