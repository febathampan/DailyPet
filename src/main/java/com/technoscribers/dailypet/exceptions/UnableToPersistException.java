package com.technoscribers.dailypet.exceptions;

public class UnableToPersistException extends Exception {
    public UnableToPersistException(String errorMessage) {
        super(errorMessage);
    }
}
