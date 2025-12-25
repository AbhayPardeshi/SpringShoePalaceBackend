package com.example.shoepalace.exception;

public class InvalidCartOperationException extends CartException{
    public InvalidCartOperationException(String message){
        super(message);
    }
}
