

export class SearchedUser {

  id:number;
  username:string;
  name:string;
  lastname:string;
  exerciseIds:number[];
  workoutIds:number[];
  planIds:number[];

  constructor(id:number, username:string, name:string,
    lastname:string, exerciseIds:number[], workoutIds:number[],
    plansIds:number[]){
      this.id = id;
      this.username = username;
      this.name = name;
      this.lastname = lastname;
      this.exerciseIds = exerciseIds;
      this.workoutIds = workoutIds;
      this.planIds = plansIds;
    }
}
