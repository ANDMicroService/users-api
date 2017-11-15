package com.andmicroservice.users.exception;

public class EmailExistsException extends Exception {

    private static final String ERROR_MESSAGE = "Email already exists";

    public EmailExistsException() {
        super(ERROR_MESSAGE);
    }
}
