package com.example.lab.exceptions;

public class RentalNotFoundException extends RuntimeException {
    public RentalNotFoundException(Long id) {
        super("Could not find rental " + id);
    }
}
