package com.example.shoepalace.controller;

import com.example.shoepalace.model.User;
import com.example.shoepalace.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    // for test purpose will create a new user after signup
    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User newUser){

        User addedUser = userService.createUser(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedUser);
    }
}
