import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { faAngleLeft, faAngleRight, faChevronRight } from '@fortawesome/free-solid-svg-icons';
import { Plan } from 'src/app/Models/plan';
import { UserExercise } from 'src/app/Models/user-exercise';
import { Workout } from 'src/app/Models/workout';

@Component({
  selector: 'user-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.scss']
})
export class DetailsComponent implements OnInit {

  @Input() details: UserExercise[] | Workout[] | Plan[] | any[] = [];

  @Output() closeDeets = new EventEmitter<boolean>();

  subDetails!: Workout | Plan;
  subDeets:boolean = false;

  closeIcon = faAngleLeft;
  angleRight = faChevronRight;

  constructor() { }

  ngOnInit(): void {
  }

  close(){
    this.closeDeets.emit(false);
  }

  getTitle(){
    if(this.isExercise(this.details)){
      return "Exercises";
    }else if(this.isWorkout(this.details)){
      return "Workouts";
    }else if(this.isPlan(this.details)){
      return "Plans";
    }
    return "ERROR";
  }

  isExercise(details: UserExercise[] | Workout[] | Plan[]): details is Workout[]{
    return (<UserExercise>details[0]).exercise !== undefined;
  }

  isWorkout(details: UserExercise[] | Workout[] | Plan[]): details is Workout[]{
    return (<Workout>details[0]).userExercises !== undefined;
  }

  isPlan(details: UserExercise[] | Workout[] | Plan[]): details is Workout[]{
    return (<Plan>details[0]).workouts !== undefined;
  }

  passToSubDetails(subDetails: Workout | Plan){
    this.subDetails = subDetails;
    this.subDeets = true;
  }

  closeSubDeets(){
    this.subDeets = false;
  }

}
