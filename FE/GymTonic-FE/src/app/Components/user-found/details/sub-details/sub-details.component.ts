import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { faAngleLeft, faAngleRight } from '@fortawesome/free-solid-svg-icons';
import { Plan } from 'src/app/Models/plan';
import { Workout } from 'src/app/Models/workout';

@Component({
  selector: 'sub-details',
  templateUrl: './sub-details.component.html',
  styleUrls: ['./sub-details.component.scss']
})
export class SubDetailsComponent implements OnInit {

  @Input() subDetailsRecieved!: Workout | Plan | any;

  @Output() closeSubDeets = new EventEmitter<boolean>();

  closeIcon = faAngleLeft;
  angleRight = faAngleRight;

  planDeets:boolean = false;
  planDetails!: Workout;

  constructor() { }

  ngOnInit(): void {
  }

  close(){
    this.closeSubDeets.emit(false)
  }

  test(x:Workout){
    this.planDeets = true;
    this.planDetails = x;
  }

  closePlanDeets(){
    this.closeSubDeets.emit(false)
  }

}
