import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Exercise } from 'src/app/Models/exercise';
import { UserExercise } from 'src/app/Models/user-exercise';
import { Workout } from 'src/app/Models/workout';
import { AuthService } from 'src/app/Services/auth.service';
import { UserExerciseService } from 'src/app/Services/user-exercise.service';
import { WorkoutService } from 'src/app/Services/workout.service';

@Component({
  selector: 'app-raw-workout-form',
  templateUrl: './raw-workout-form.component.html',
  styleUrls: ['./raw-workout-form.component.scss']
})
export class RawWorkoutFormComponent implements OnInit {

  @Output() outputWorkout = new EventEmitter<Workout>();

  form!: FormGroup;
  formIsValid: boolean = true;
  userExercises: UserExercise[] = [];

  newExercises: UserExercise[] = [];

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

  addRawExercise(ex:UserExercise){
    this.newExercises.push(ex)
  }

  removeExercise(ex:UserExercise){
    const index = this.newExercises.indexOf(ex, 0);
    this.newExercises.splice(index, 1);
  }

  postNewWorkout(){
    let userId:number = this.auth.getLoggedUser().id;
    if(this.form.value.name != null && this.newExercises.length > 0){
      this.workSvc.postNewWorkout(userId, new Workout(this.form.value.name, this.newExercises))
      .subscribe(res => {
        this.outputWorkout.emit(res)
        this.form.reset();
        this.newExercises = [];
      })
    }
  }



}
