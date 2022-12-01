import { Component, Input, OnInit } from '@angular/core';
import { faAngleDown, faAngleUp } from '@fortawesome/free-solid-svg-icons';
import { Plan } from 'src/app/Models/plan';
import { UserExercise } from 'src/app/Models/user-exercise';
import { Workout } from 'src/app/Models/workout';

@Component({
  selector: 'app-plan-card',
  templateUrl: './plan-card.component.html',
  styleUrls: ['./plan-card.component.scss']
})
export class PlanCardComponent implements OnInit {

  angleDown = faAngleDown
  angleUp = faAngleUp;

  @Input() plan!: Plan;
  @Input() userWorkouts!: Workout[];
  @Input() userExercises!: UserExercise[];

  expand:boolean = false;

  constructor() { }

  ngOnInit(): void {
  }

}
