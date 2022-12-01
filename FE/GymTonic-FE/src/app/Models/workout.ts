import { Exercise } from "./exercise";

export class Workout {
  id!:number;
  name:string;
  userExercises:Exercise[] = [];

  constructor(name:string){
    this.name = name;
  }
}
