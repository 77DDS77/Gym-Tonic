import { Component, Input, OnInit } from '@angular/core';
import { faPencilAlt, faTrash } from '@fortawesome/free-solid-svg-icons';
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

  deleted:boolean = false;

  faEdit = faPencilAlt
  faTrash = faTrash;

  constructor(
    private wrkSvc:WorkoutService,
    private auth:AuthService
    ) { }

  ngOnInit(): void {
  }

  deleteWorkout(workoutId:number){
    let userId = this.auth.getLoggedUser().id;
    this.wrkSvc.deleteWorkout(userId, workoutId)
    .subscribe(() => {
      this.deleted = true;
    })

  }

}
