package com.example.lab.exceptions;

public class VHSNotFoundException extends RuntimeException {
    public VHSNotFoundException(Long id) {
        super("Could not find VHS " + id);
    }
}
