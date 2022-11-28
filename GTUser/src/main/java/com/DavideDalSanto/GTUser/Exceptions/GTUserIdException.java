package com.DavideDalSanto.GTUser.Exceptions;

public class GTUserIdException extends Exception {
    public GTUserIdException(Long id) {
        super("User with id (" + id + ") not found.");
    }
}
