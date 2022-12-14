import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { faArrowRight, faChevronRight, faCross, faEdit, faMarker, faPenSquare, faTimes, faUserMinus, faUserPlus, faXRay } from '@fortawesome/free-solid-svg-icons';
import { Plan } from 'src/app/Models/plan';
import { SearchedUser } from 'src/app/Models/searchedUser';
import { UserExercise } from 'src/app/Models/user-exercise';
import { Workout } from 'src/app/Models/workout';
import { AuthService } from 'src/app/Services/auth.service';
import { UserExerciseService } from 'src/app/Services/user-exercise.service';
import { PtService } from 'src/app/Services/pt.service';
import { WorkoutService } from 'src/app/Services/workout.service';
import { PlanService } from 'src/app/Services/plan.service';

@Component({
  selector: 'user-found',
  templateUrl: './user-found.component.html',
  styleUrls: ['./user-found.component.scss']
})
export class UserFoundComponent implements OnInit {

  @Input() user!:SearchedUser;

  @Output() followedUser = new EventEmitter<boolean>();

  userPlus = faUserPlus;
  userMinus = faUserMinus;
  angleRight = faChevronRight;
  edit = faArrowRight;
  closeEdit= faTimes;

  followed:boolean = false;
  deets:boolean = false;
  editing:boolean = false;
  inputDetails:UserExercise[] | Workout[] | Plan[] = [];

  constructor(
    private auth:AuthService,
    private ptSvc:PtService,
    private uexSvc:UserExerciseService,
    private wrkSvc:WorkoutService,
    private planSvc:PlanService,
    ) { }

  ngOnInit(): void {
    this.checkIfFollowed();
  }

  checkIfFollowed(){
    let ptAuth = this.auth.getLoggedUser();
    this.ptSvc.getPtById(ptAuth.id)
    .subscribe(pt => {
      if(pt.gtUserIds.includes(this.user.id)){
        this.followed = true;
      }
    })
  }

  toggleFollowUser(){
    let ptAuth = this.auth.getLoggedUser();
    this.ptSvc.getPtById(ptAuth.id)
    .subscribe(pt => {
      if(this.followed == false){
        pt.gtUserIds.push(this.user.id);
        this.ptSvc.updatePT(pt)
        .subscribe(() => {
          this.followed = true;
          //output followed user
          this.followedUser.emit(true)
        })
      }else{
        const index = pt.gtUserIds.indexOf(this.user.id, 0);
        pt.gtUserIds.splice(index, 1);
        this.ptSvc.updatePT(pt)
        .subscribe(() => {
          this.followed = false;
          //output unfollowed user
          this.followedUser.emit(false)
        })
      }
    })
  }

  exerciseDeets(){
    this.uexSvc.getUserExercises(this.user.id)
    .subscribe(exx => {
      this.inputDetails = [];
      this.inputDetails = exx;
      this.deets = true;
    })
  }

  workoutDeets(){
    this.wrkSvc.getUserWorkouts(this.user.id)
    .subscribe(wrks => {
      this.inputDetails = [];
      this.inputDetails = wrks;
      this.deets = true;
    })
  }

  planDeets(){
    this.planSvc.getUserPlans(this.user.id)
    .subscribe(plans => {
      this.inputDetails = [];
      this.inputDetails = plans;
      this.deets = true;
    })
  }

  closeDeets(){
    this.deets = false;
  }

  editUser(){
    this.editing = !this.editing;
  }

}
