import { Workout } from "./workout";

export class Plan {

  id!:number;
  name:string;
  workouts:Workout[] = [];

  constructor(name:string){
    this.name = name;
  }
}
