package com.DavideDalSanto.GTUser.Exceptions;

public class GTPTIdException extends Exception {
    public GTPTIdException(Long id) {
        super("Personal Trainer with id " + id + " not found.");
    }
}
