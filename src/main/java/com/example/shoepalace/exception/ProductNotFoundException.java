package com.example.shoepalace.exception;

public class ProductNotFoundException extends CartException{
    public ProductNotFoundException(String message) {
        super(message);
    }
}
