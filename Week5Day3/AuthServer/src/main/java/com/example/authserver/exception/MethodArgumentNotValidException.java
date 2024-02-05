package com.example.authserver.exception;

public class MethodArgumentNotValidException extends Exception{
    public MethodArgumentNotValidException(String message) {
        super(message);
    }
}
