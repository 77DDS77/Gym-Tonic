import { Component, Input, OnInit } from '@angular/core';
import { faPencilAlt, faTrash } from '@fortawesome/free-solid-svg-icons';
import { Workout } from 'src/app/Models/workout';

@Component({
  selector: 'app-data-workout',
  templateUrl: './data-workout.component.html',
  styleUrls: ['./data-workout.component.scss']
})
export class DataWorkoutComponent implements OnInit {

  @Input() wrk!:Workout;

  faEdit = faPencilAlt
  faTrash = faTrash;

  constructor() { }

  ngOnInit(): void {
  }

}
