import { Component, Input, OnInit } from '@angular/core';
import { faDumbbell, faScroll, faLayerGroup } from '@fortawesome/free-solid-svg-icons';
import { Plan } from 'src/app/Models/plan';
import { SearchedUser } from 'src/app/Models/searchedUser';
import { UserExercise } from 'src/app/Models/user-exercise';
import { Workout } from 'src/app/Models/workout';
import { PlanService } from 'src/app/Services/plan.service';
import { UserExerciseService } from 'src/app/Services/user-exercise.service';
import { WorkoutService } from 'src/app/Services/workout.service';

@Component({
  selector: 'manage-user',
  templateUrl: './manage-user.component.html',
  styleUrls: ['./manage-user.component.scss']
})
export class ManageUserComponent implements OnInit {

  @Input() user!:SearchedUser;

  userExercises: UserExercise[] = [];
  userWorkouts: Workout[] = [];
  userPlans: Plan[] = [];

  exercise = faDumbbell;
  workout = faScroll;
  plan = faLayerGroup;

  exx:boolean = true;
  wrks:boolean = false;
  plns:boolean = false;
  addEx:boolean = false;

  constructor(
    private exSvc:UserExerciseService,
    private wrkSvc:WorkoutService,
    private plnSvc:PlanService
  ) { }

  ngOnInit(): void {
    this.exSvc.getUserExercises(this.user.id)
    .subscribe(exx => this.userExercises = exx)
    this.wrkSvc.getUserWorkouts(this.user.id)
    .subscribe(workouts => this.userWorkouts = workouts);
    this.plnSvc.getUserPlans(this.user.id)
    .subscribe(plans => this.userPlans = plans);
  }

  showExercises(){
    this.exx = true;
    this.wrks = false;
    this.plns = false;
  }

  showWorkouts(){
    this.exx = false;
    this.wrks = true;
    this.plns = false;
  }

  showPlans(){
    this.exx = false;
    this.wrks = false;
    this.plns = true;
  }

  exDeleted(value:boolean, ex:UserExercise){
    const index = this.userExercises.indexOf(ex, 0);
    this.userExercises.splice(index, 1);
  }

  addExercise(ex:UserExercise){
    this.userExercises.push(ex);
    this.addEx =false;
  }

}
