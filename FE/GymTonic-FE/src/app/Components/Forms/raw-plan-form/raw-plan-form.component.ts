import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Plan } from 'src/app/Models/plan';
import { Workout } from 'src/app/Models/workout';
import { AuthService } from 'src/app/Services/auth.service';
import { PlanService } from 'src/app/Services/plan.service';

@Component({
  selector: 'app-raw-plan-form',
  templateUrl: './raw-plan-form.component.html',
  styleUrls: ['./raw-plan-form.component.scss']
})
export class RawPlanFormComponent implements OnInit {

  planForm!: FormGroup;

  newWorkouts:Workout[] = [];

  constructor(
    private planSvc:PlanService,
    private auth:AuthService) { }

  ngOnInit(): void {
    this.planForm = new FormGroup({
      name: new FormControl(null, Validators.required),
    });
  }

  addWorkout(work:Workout){
    this.newWorkouts.push(work);
  }
  removeWorkout(work:Workout){
    const index = this.newWorkouts.indexOf(work, 0);
    this.newWorkouts.splice(index, 1);
  }

  postNewPlan(){
    let userId:number = this.auth.getLoggedUser().id;
    this.planSvc.postNewPlan(userId, new Plan(this.planForm.value.name, this.newWorkouts))
    .subscribe(res => {
      this.planForm.reset();
      this.newWorkouts = [];
    })

  }

}
