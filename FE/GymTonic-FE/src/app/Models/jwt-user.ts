
type role = { id: number, roleType: string }[]

export abstract class JwtUser {

  id!:number;
  username:string;
  email:string;
  name:string;
  lastname:string;
  password:string;
  roles!:string[];
  userExercisesId!:number[];
  userWorkoutsId!:number[];
  userPlansIds!:number[];

  constructor(username:string, email:string, name:string, lastname:string, password:string){
    this.username = username;
    this.email = email;
    this.name = name;
    this.lastname = lastname;
    this.password = password;
  }
}
