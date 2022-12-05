import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Plan } from 'src/app/Models/plan';
import { UserExercise } from 'src/app/Models/user-exercise';
import { Workout } from 'src/app/Models/workout';
import { AuthService } from 'src/app/Services/auth.service';
import { PlanService } from 'src/app/Services/plan.service';
import { UserExerciseService } from 'src/app/Services/user-exercise.service';
import { WorkoutService } from 'src/app/Services/workout.service';

@Component({
  selector: 'app-plan-form',
  templateUrl: './plan-form.component.html',
  styleUrls: ['./plan-form.component.scss']
})
export class PlanFormComponent implements OnInit {

  form!: FormGroup;
  formIsValid: boolean = true;
  userWorkouts: Workout[] = [];
  choosenWorkouts: Workout[] = [];

  constructor(
    private uExScv:UserExerciseService,
    private auth:AuthService,
    private workSvc:WorkoutService,
    private planSvc:PlanService) { }

  ngOnInit(): void {
    this.getUserWorkouts();

    this.form = new FormGroup({
      name: new FormControl(null, Validators.required),
      muscle: new FormControl(null, Validators.required),
      workout: new FormControl(null, Validators.required)
    })
  }

  getUserWorkouts(){
    let userId:number = this.auth.getLoggedUser().id;
    this.workSvc.getUserWorkouts(userId)
    .subscribe(works => {
      this.userWorkouts = works;
    })
  }

  addWorkout(){
    if(this.form.value.workout != null){
      this.choosenWorkouts.push(this.form.value.workout);
    }
  }

  removeWorkout(ex:Workout){
    const index = this.choosenWorkouts.indexOf(ex, 0);
    this.choosenWorkouts.splice(index, 1);
  }

  postNewWorkout(){
    let userId:number = this.auth.getLoggedUser().id;
    if(this.form.value.name != null && this.choosenWorkouts.length > 0){
      this.planSvc.postNewPlan(userId, new Plan(this.form.value.name, this.choosenWorkouts))
      .subscribe(res => {
        console.log(res);
        this.form.reset();
        this.choosenWorkouts = [];
      })
    }
  }

}
