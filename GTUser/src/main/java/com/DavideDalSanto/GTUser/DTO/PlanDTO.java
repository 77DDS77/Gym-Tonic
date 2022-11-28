package com.DavideDalSanto.GTUser.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanDTO implements Serializable {
    private Long id;
    private String name;
    private List<WorkoutDTO> workouts;
}