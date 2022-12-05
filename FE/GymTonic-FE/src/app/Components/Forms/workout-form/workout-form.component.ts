import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { UserExercise } from 'src/app/Models/user-exercise';
import { Workout } from 'src/app/Models/workout';
import { AuthService } from 'src/app/Services/auth.service';
import { UserExerciseService } from 'src/app/Services/user-exercise.service';
import { WorkoutService } from 'src/app/Services/workout.service';

@Component({
  selector: 'app-workout-form',
  templateUrl: './workout-form.component.html',
  styleUrls: ['./workout-form.component.scss']
})
export class WorkoutFormComponent implements OnInit {

  form!: FormGroup;
  formIsValid: boolean = true;
  userExercises: UserExercise[] = [];
  choosenExercises: UserExercise[] = [];

  constructor(
    private uExScv:UserExerciseService,
    private auth:AuthService,
    private workSvc:WorkoutService) { }

  ngOnInit(): void {
    this.getUserExercises();

    this.form = new FormGroup({
      name: new FormControl(null, Validators.required),
      muscle: new FormControl(null, Validators.required),
      exercise: new FormControl(null, Validators.required)
    })
  }

  getUserExercises(){
    let userId:number = this.auth.getLoggedUser().id;
    this.uExScv.getUserExercises(userId)
    .subscribe(exx => {
      this.userExercises = exx;
    })
  }

  addExercise(){
    if(this.form.value.exercise != null){
      this.choosenExercises.push(this.form.value.exercise);
    }
  }

  removeExercise(ex:UserExercise){
    const index = this.choosenExercises.indexOf(ex, 0);
    this.choosenExercises.splice(index, 1);
  }

  postNewWorkout(){
    let userId:number = this.auth.getLoggedUser().id;
    if(this.form.value.name != null && this.choosenExercises.length > 0){
      this.workSvc.postNewWorkout(userId, new Workout(this.form.value.name, this.choosenExercises))
      .subscribe(res => {
        console.log(res);
        this.form.reset();
        this.choosenExercises = [];
      })
    }
  }

}
