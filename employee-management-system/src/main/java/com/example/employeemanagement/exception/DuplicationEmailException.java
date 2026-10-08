package com.example.employeemanagement.exception;

public class DuplicationEmailException extends RuntimeException {

    public DuplicationEmailException(String message) {
        super(message);
    }
}
