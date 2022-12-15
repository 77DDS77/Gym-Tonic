import { Component, Input, OnInit } from '@angular/core';
import { Plan } from 'src/app/Models/plan';
import { Workout } from 'src/app/Models/workout';

@Component({
  selector: 'manage-plan',
  templateUrl: './manage-plan.component.html',
  styleUrls: ['./manage-plan.component.scss']
})
export class ManagePlanComponent implements OnInit {

  @Input() plan!:Plan;
  @Input() userId!:number;

  constructor() { }

  ngOnInit(): void {
  }

}
