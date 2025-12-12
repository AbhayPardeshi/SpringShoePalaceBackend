package com.example.shoepalace.controller;

import com.example.shoepalace.dto.SignupRequest;
import com.example.shoepalace.model.User;
import com.example.shoepalace.services.UserService;
import jakarta.validation.Valid;
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

    @PostMapping(path = "/signup")
    public ResponseEntity<String> signupNewUser(@Valid @RequestBody SignupRequest request){
        User savedUser = userService.signupUser(request);
        String message;

        if(savedUser != null){
            message = "Successful new user signup, Please Login!";
        }else{
            message = "not successful";
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }
}
