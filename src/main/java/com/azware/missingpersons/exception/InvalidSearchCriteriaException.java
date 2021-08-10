package com.azware.missingpersons.exception;

public class InvalidSearchCriteriaException extends RuntimeException {

    public InvalidSearchCriteriaException() {
        super("Invalid serach criteria");
    }

    public InvalidSearchCriteriaException(String message) {
        super(message);
    }

    public InvalidSearchCriteriaException(String message, Throwable cause) {
        super(message, cause);
    }
}