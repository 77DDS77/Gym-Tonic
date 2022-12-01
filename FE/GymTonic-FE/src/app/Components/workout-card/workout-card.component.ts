import { Component, Input, OnInit } from '@angular/core';
import { UserExercise } from 'src/app/Models/user-exercise';
import { Workout } from 'src/app/Models/workout';

@Component({
  selector: 'app-workout-card',
  templateUrl: './workout-card.component.html',
  styleUrls: ['./workout-card.component.scss']
})
export class WorkoutCardComponent implements OnInit {

  @Input() workout!: Workout;
  @Input() userExercises!: UserExercise[];

  constructor() { }

  ngOnInit(): void {
  }

}
