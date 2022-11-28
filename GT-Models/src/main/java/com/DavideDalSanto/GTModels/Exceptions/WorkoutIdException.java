package com.DavideDalSanto.GTModels.Exceptions;

public class WorkoutIdException extends Exception {
    public WorkoutIdException(Long id) {
        super("Workout with id (" + id + ") not found.");
    }
}
