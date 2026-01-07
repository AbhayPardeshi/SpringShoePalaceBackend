package com.example.shoepalace.exception;

public class InvalidOrderOperationException extends RuntimeException{
    public InvalidOrderOperationException(String message) {
        super(message);
    }
}
