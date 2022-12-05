import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { faAngleDown, faAngleUp, faPencilAlt, faTrash } from '@fortawesome/free-solid-svg-icons';
import { Plan } from 'src/app/Models/plan';
import { UserExercise } from 'src/app/Models/user-exercise';
import { Workout } from 'src/app/Models/workout';
import { AuthService } from 'src/app/Services/auth.service';
import { PlanService } from 'src/app/Services/plan.service';
import { UserService } from 'src/app/Services/user.service';

@Component({
  selector: 'app-plan-card',
  templateUrl: './plan-card.component.html',
  styleUrls: ['./plan-card.component.scss']
})
export class PlanCardComponent implements OnInit {

  angleDown = faAngleDown
  angleUp = faAngleUp;
  faEdit = faPencilAlt
  faTrash = faTrash;

  @Input() plan!: Plan;
  @Input() userWorkouts!: Workout[];
  @Input() userExercises!: UserExercise[];

  expand:boolean = false;

  constructor(
    private planSvc:PlanService,
    private auth:AuthService,
    private userSvc:UserService,
    private router:Router
    ) { }

  ngOnInit(): void {
  }

  deletePlan(plan:Plan){
    let userId:number = this.auth.getLoggedUser().id;
    this.userSvc.getById(userId)
    .subscribe(user => {
      if(user.userPlansIds.includes(plan.id)){
        this.planSvc.deletePlan(user.id, plan.id)
        .subscribe(res => {
          console.log("plan deleted");
          this.router.navigate(['/user-home']);
        })
      }
    })
  }

}
