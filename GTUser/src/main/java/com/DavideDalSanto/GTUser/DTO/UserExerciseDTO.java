package com.DavideDalSanto.GTUser.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserExerciseDTO {

    private Long id;
    private ExerciseDTO exercise;
    private int reps;
    private int series;
}
