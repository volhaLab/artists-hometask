package com.example.hometask.configuration;

public class BadRequestException extends RuntimeException {

    public BadRequestException() {

    }

    public BadRequestException(String message) {

        super(message);
    }
}
