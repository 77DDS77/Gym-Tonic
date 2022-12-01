import { Exercise } from "./exercise";

export class UserExercise {
  id!:number;
  exercise:Exercise;
  reps:number;
  series:number;

  constructor(exercise:Exercise, reps:number, series:number) {
    this.exercise = exercise;
    this.reps = reps;
    this.series = series;
  }
}
