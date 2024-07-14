package com.example.lab.exceptions;

public class RentalExistingForVHSException extends RuntimeException {
    public RentalExistingForVHSException(Long id) {
        super("Rental for vhs_id " + id + " already exists.");
    }
}
