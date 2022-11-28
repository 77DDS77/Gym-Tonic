package com.DavideDalSanto.GTModels.Exceptions;

public class UserExerciseIdException extends Exception {
    public UserExerciseIdException(Long id) {
        super("Exercise with ID (" + id + ") not found");
    }
}
