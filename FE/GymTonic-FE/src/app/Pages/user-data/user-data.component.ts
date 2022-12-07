import { Component, OnInit } from '@angular/core';
import { faPencilAlt, faTrash } from '@fortawesome/free-solid-svg-icons';
import { UserExercise } from 'src/app/Models/user-exercise';
import { Workout } from 'src/app/Models/workout';
import { AuthService } from 'src/app/Services/auth.service';
import { UserExerciseService } from 'src/app/Services/user-exercise.service';
import { WorkoutService } from 'src/app/Services/workout.service';

@Component({
  selector: 'app-user-data',
  templateUrl: './user-data.component.html',
  styleUrls: ['./user-data.component.scss']
})
export class UserDataComponent implements OnInit {

  userExercises:UserExercise[] = [];
  userWorkout:Workout[] = [];

  faEdit = faPencilAlt
  faTrash = faTrash;

  constructor(
    private auth:AuthService,
    private uexSvc:UserExerciseService,
    private wrkSvc:WorkoutService
    ) { }

  ngOnInit(): void {
    let userId = this.auth.getLoggedUser().id;
    this.uexSvc.getUserExercises(userId).subscribe(exx => this.userExercises = exx);
    this.wrkSvc.getUserWorkouts(userId).subscribe(wrkx => this.userWorkout = wrkx);
  }

  addExerciseFromChildOutput(ex:UserExercise){
    this.userExercises.push(ex);
  }

}
