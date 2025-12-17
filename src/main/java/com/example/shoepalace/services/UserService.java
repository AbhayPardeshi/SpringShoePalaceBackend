package com.example.shoepalace.services;

import com.example.shoepalace.exception.EmailNotFoundException;
import com.example.shoepalace.exception.IncorrectPasswordException;
import com.example.shoepalace.requestDTO.LoginRequest;
import com.example.shoepalace.requestDTO.SignupRequest;
import com.example.shoepalace.exception.EmailAlreadyUsedException;
import com.example.shoepalace.model.User;
import com.example.shoepalace.repository.UserRepository;
import com.example.shoepalace.responseDTO.JWTResponseDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public UserService(PasswordEncoder passwordEncoder,UserRepository userRepository,JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }


    public User createUser(User newUserToBeAdded){
        return userRepository.save(newUserToBeAdded);
    }

    public User signupUser(SignupRequest request){

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyUsedException("Email already registered");
        }

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

    public JWTResponseDTO loginUser(LoginRequest request){
        String userEmail = request.getEmail();
        JWTResponseDTO responseDTO = new JWTResponseDTO();

            String plainPassword = request.getPassword();

            User storedUser = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new EmailNotFoundException("Email not found"));

            if (!passwordEncoder.matches(plainPassword, storedUser.getPasswordHash())) {
                throw new IncorrectPasswordException("Invalid password");
            }

            String jwtToken = jwtService.generateToken(storedUser);
            responseDTO.setAccessToken(jwtToken);
            responseDTO.setRefreshToken("ref token");
            return responseDTO;
    }
}
