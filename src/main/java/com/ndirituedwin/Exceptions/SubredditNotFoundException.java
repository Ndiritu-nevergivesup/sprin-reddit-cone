package com.ndirituedwin.Exceptions;

public class SubredditNotFoundException extends RuntimeException{
    public SubredditNotFoundException(String exmessage) {
        super(exmessage);
    }

}
