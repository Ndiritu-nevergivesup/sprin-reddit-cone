package com.ndirituedwin.Exceptions;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(String s) {
        super(s);
    }
}
