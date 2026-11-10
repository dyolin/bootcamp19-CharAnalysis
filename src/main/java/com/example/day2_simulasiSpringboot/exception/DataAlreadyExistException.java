package com.example.day2_simulasiSpringboot.exception;

public class DataAlreadyExistException extends RuntimeException {
    public DataAlreadyExistException(String kalimat) {
        super("Kalimat " +kalimat+ " is already exist");
    }
}
