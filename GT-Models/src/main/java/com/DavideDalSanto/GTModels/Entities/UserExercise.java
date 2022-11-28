package com.DavideDalSanto.GTModels.Entities;

import lombok.*;

import javax.persistence.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user_exercises")
public class UserExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "db_exercise_id")
    private Exercise exercise;

    private int reps;
    private int series;

//    @ManyToMany
//    @JoinTable(name = "workouts_user_exercises",
//            joinColumns = @JoinColumn(name = "workout_id"),
//            inverseJoinColumns = @JoinColumn(name = "user_exercises_id"))
//    private List<Workout> workouts = new ArrayList<>();

    //V2 fare string Note per segnare peso o PR
}
