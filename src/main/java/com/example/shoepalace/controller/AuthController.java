package com.example.shoepalace.controller;

import com.example.shoepalace.requestDTO.LoginRequest;
import com.example.shoepalace.requestDTO.SignupRequest;
import com.example.shoepalace.model.User;
import com.example.shoepalace.responseDTO.JWTResponseDTO;
import com.example.shoepalace.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
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
            message = "User registered, Please Login!";
        }else{
            message = "not successful";
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @PostMapping(path = "/login")
        public ResponseEntity<JWTResponseDTO> userLogin(@Valid @RequestBody LoginRequest loginRequest){
        JWTResponseDTO userTokens = userService.loginUser(loginRequest);

        return ResponseEntity.ok().body(userTokens);
    }

//    @GetMapping
//        public ResponseEntity<ArrayList<Product>> wishListItems(){
//
//    }
}
