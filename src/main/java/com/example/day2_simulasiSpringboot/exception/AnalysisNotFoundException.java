package com.example.day2_simulasiSpringboot.exception;

public class AnalysisNotFoundException extends RuntimeException {
    public AnalysisNotFoundException(Long id) {
        super("Data dengan id " +id+ " tidak ditemukan");
    }
}
