import { Component, Input, OnInit } from '@angular/core';
import { Workout } from 'src/app/Models/workout';

@Component({
  selector: 'manage-workout',
  templateUrl: './manage-workout.component.html',
  styleUrls: ['./manage-workout.component.scss']
})
export class ManageWorkoutComponent implements OnInit {

  @Input() workout!:Workout;
  @Input() userId!:number;

  constructor() { }

  ngOnInit(): void {
  }

}
