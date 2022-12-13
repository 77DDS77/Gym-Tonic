import { Exercise } from "./exercise";
import { UserExercise } from "./user-exercise";

export class Workout {
  id!:number;
  name:string;
  userExercises:UserExercise[] = [];

  constructor(name:string, userExs:UserExercise[]){
    this.name = name;
    this.userExercises = userExs;

  }

}
