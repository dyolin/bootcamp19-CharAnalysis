package com.example.day2_simulasiSpringboot.exception;

public class QuoteNotFoundException extends RuntimeException {
    public QuoteNotFoundException(Long id) {
        super("Quote with ID " +id+ " was not found");
    }
}
