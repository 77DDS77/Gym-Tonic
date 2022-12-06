import { Component, Input, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { faPencilAlt, faTrash } from '@fortawesome/free-solid-svg-icons';
import { UserExercise } from 'src/app/Models/user-exercise';
import { Workout } from 'src/app/Models/workout';
import { AuthService } from 'src/app/Services/auth.service';
import { WorkoutService } from 'src/app/Services/workout.service';

@Component({
  selector: 'app-data-workout',
  templateUrl: './data-workout.component.html',
  styleUrls: ['./data-workout.component.scss']
})
export class DataWorkoutComponent implements OnInit {

  @Input() wrk!:Workout;
  editWorkout!:FormGroup

  workoutExercises: UserExercise[] = [];

  deleted:boolean = false;

  faEdit = faPencilAlt
  faTrash = faTrash;

  constructor(
    private wrkSvc:WorkoutService,
    private auth:AuthService
    ) { }

  ngOnInit(): void {
    this.workoutExercises = this.wrk.userExercises;
    this.editWorkout = new FormGroup({
      name: new FormControl('', Validators.required)
    })
  }

  removeExercise(ex:UserExercise){
    const index = this.workoutExercises.indexOf(ex, 0);
    this.workoutExercises.splice(index, 1);
  }

  updateWorkout(){}


  deleteWorkout(workoutId:number){
    let userId = this.auth.getLoggedUser().id;
    this.wrkSvc.deleteWorkout(userId, workoutId)
    .subscribe(() => {
      this.deleted = true;
    })

  }

}
