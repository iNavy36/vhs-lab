package com.example.lab.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RentalExistingForVHSAdvice {
    @ExceptionHandler(RentalExistingForVHSException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String rentalNotFoundHandler(RentalExistingForVHSException ex) {
        return ex.getMessage();
    }
}
