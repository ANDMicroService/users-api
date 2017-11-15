package com.andmicroservice.users.exception;

public class IdProvidedException extends Exception {

    private static final String ERROR_MESSAGE = "ID must not be provided when creating new entities";

    public IdProvidedException() {
        super(ERROR_MESSAGE);
    }

}
