import { Component, OnInit } from '@angular/core';
import { NavfootComponent } from 'src/app/Components/navfoot/navfoot.component';
import { JwtUser } from 'src/app/Models/jwt-user';
import { Plan } from 'src/app/Models/plan';
import { UserExercise } from 'src/app/Models/user-exercise';
import { Workout } from 'src/app/Models/workout';
import { AuthService } from 'src/app/Services/auth.service';
import { PlanService } from 'src/app/Services/plan.service';
import { UserExerciseService } from 'src/app/Services/user-exercise.service';
import { UserService } from 'src/app/Services/user.service';
import { WorkoutService } from 'src/app/Services/workout.service';

@Component({
  selector: 'app-user-home',
  templateUrl: './user-home.component.html',
  styleUrls: ['./user-home.component.scss']
})
export class UserHomeComponent implements OnInit {

  user!:JwtUser | null;
  userPlans!:Plan[];

  constructor(
    private userSrv: UserService,
    private authSrv: AuthService,
    private uPlanSrv: PlanService
  ) { }

  ngOnInit(): void {
    let user:any = this.authSrv.getLoggedUser();
    this.userSrv.getById(user.id)
    .subscribe(user => {
      this.user = user;

      this.uPlanSrv.getUserPlans(user.id)
      .subscribe(plans => {
        this.userPlans = plans;
        console.log(this.userPlans);

      })
    })
  }

}
