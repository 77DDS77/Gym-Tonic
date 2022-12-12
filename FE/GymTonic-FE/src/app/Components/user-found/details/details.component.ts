import { Component, Input, OnInit } from '@angular/core';
import { GTUser } from 'src/app/Models/gtuser';
import { Plan } from 'src/app/Models/plan';
import { SearchedUser } from 'src/app/Models/searchedUser';
import { UserExercise } from 'src/app/Models/user-exercise';
import { Workout } from 'src/app/Models/workout';

@Component({
  selector: 'user-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.scss']
})
export class DetailsComponent implements OnInit {

  @Input() details:UserExercise[] | Workout[] | Plan[] = [];

  constructor() { }

  ngOnInit(): void {
  }

}
