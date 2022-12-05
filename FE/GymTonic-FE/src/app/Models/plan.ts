import { Workout } from "./workout";

export class Plan {

  id!:number;
  name:string;
  workouts:Workout[] = [];

  constructor(name:string, workouts:Workout[]){
    this.name = name;
    this.workouts = workouts;
  }
}
