package com.cleanhub.codingchallenge.exceptions;

/**
 * This exception for throwing User input validation exception.
 */
public class ValidationException extends Exception {
    public ValidationException(String msg) {
        super(msg);
    }
}
