import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { faAngleLeft, faAngleRight, faChevronRight } from '@fortawesome/free-solid-svg-icons';
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
  angleRight = faChevronRight;

  planDeets:boolean = false;
  planDetails!: Workout;

  constructor() { }

  ngOnInit(): void {
  }

  close(){
    this.closeSubDeets.emit(false)
  }

  closeSubDetails(x:Workout){
    this.planDeets = true;
    this.planDetails = x;
  }

  closePlanDeets(){
    // this.closeSubDeets.emit(false)
    this.planDeets = false;
  }

}
