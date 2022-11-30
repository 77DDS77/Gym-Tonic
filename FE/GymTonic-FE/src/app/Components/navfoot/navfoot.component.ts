import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-navfoot',
  templateUrl: './navfoot.component.html',
  styleUrls: ['./navfoot.component.scss']
})
export class NavfootComponent implements OnInit {

  exerciseUp:boolean = false;
  workoutUp:boolean = false;
  planUp:boolean = false;

  constructor() { }

  ngOnInit(): void {
  }

  triggerActions(){
    this.exerciseUp = !this.exerciseUp;
    setTimeout(() => {
      this.workoutUp = !this.workoutUp;
    },100)
    setTimeout(() => {
      this.planUp = !this.planUp;
    },100)
  }

}
