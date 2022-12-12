

export class SearchedUser {

  id:number;
  username:string;
  name:string;
  lastname:string;
  userExercisesId:number[];
  userWorkoutsId:number[];
  userPlansIds:number[];

  constructor(id:number, username:string, name:string,
    lastname:string, exerciseIds:number[], workoutIds:number[],
    plansIds:number[]){
      this.id = id;
      this.username = username;
      this.name = name;
      this.lastname = lastname;
      this.userExercisesId = exerciseIds;
      this.userWorkoutsId = workoutIds;
      this.userPlansIds = plansIds;
    }
}
