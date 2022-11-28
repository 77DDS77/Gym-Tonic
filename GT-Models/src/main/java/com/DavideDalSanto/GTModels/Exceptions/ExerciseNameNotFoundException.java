package com.DavideDalSanto.GTModels.Exceptions;

public class ExerciseNameNotFoundException extends Exception {
    public ExerciseNameNotFoundException(String name) {
        super("Exercise \"" + name + "\" not found in our Database.");
    }
}
