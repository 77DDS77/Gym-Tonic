package com.DavideDalSanto.GTModels.Exceptions;

public class PlanIdException extends Exception {
    public PlanIdException(Long id) {
        super("Plan with id (" + id + ") not found.");
    }
}
