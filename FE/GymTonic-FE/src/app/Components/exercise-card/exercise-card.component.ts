import { Component, Input, OnInit } from '@angular/core';
import { faAngleDown, faAngleUp, faTimes } from '@fortawesome/free-solid-svg-icons';
import { UserExercise } from 'src/app/Models/user-exercise';

@Component({
  selector: 'app-exercise-card',
  templateUrl: './exercise-card.component.html',
  styleUrls: ['./exercise-card.component.scss']
})
export class ExerciseCardComponent implements OnInit {

  @Input() exercise!: UserExercise;

  times = faTimes;

  constructor() { }

  ngOnInit(): void {
  }

}
