package com.cleanhub.codingchallenge.exceptions;

/**
 * Custom exception class for CleanHub service exceptions
 */
public class CleanHubServiceException extends Exception {
    public CleanHubServiceException(String msg) {
        super(msg);
    }
}
