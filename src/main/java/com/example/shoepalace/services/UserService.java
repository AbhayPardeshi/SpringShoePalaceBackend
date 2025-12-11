package com.example.shoepalace.services;

import com.example.shoepalace.model.User;
import com.example.shoepalace.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User createUser(User newUserToBeAdded){
        return userRepository.save(newUserToBeAdded);
    }
}
