package com.andmicroservice.users.exception;

public class LoginExistsException extends Exception {

    private static final String ERROR_MESSAGE = "Login already exists";

    public LoginExistsException() {
        super(ERROR_MESSAGE);
    }
}
