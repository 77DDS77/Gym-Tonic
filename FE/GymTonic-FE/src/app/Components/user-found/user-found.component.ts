import { Component, Input, OnInit } from '@angular/core';
import { faAngleRight, faChevronRight, faUserMinus, faUserPlus } from '@fortawesome/free-solid-svg-icons';
import { Plan } from 'src/app/Models/plan';
import { SearchedUser } from 'src/app/Models/searchedUser';
import { UserExercise } from 'src/app/Models/user-exercise';
import { Workout } from 'src/app/Models/workout';
import { AuthService } from 'src/app/Services/auth.service';
import { PtService } from 'src/app/Services/pt.service';
import { UserService } from 'src/app/Services/user.service';

@Component({
  selector: 'user-found',
  templateUrl: './user-found.component.html',
  styleUrls: ['./user-found.component.scss']
})
export class UserFoundComponent implements OnInit {

  @Input() user!:SearchedUser;

  userPlus = faUserPlus;
  userMinus = faUserMinus;
  angleRight = faChevronRight;

  followed:boolean = false;
  outputDetails:UserExercise[] | Workout[] | Plan[] = [];

  constructor(
    private auth:AuthService,
    private ptSvc:PtService,
    private userSvc:UserService) { }

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
        })
      }else{
        const index = pt.gtUserIds.indexOf(this.user.id, 0);
        pt.gtUserIds.splice(index, 1);
        this.ptSvc.updatePT(pt)
        .subscribe(() => {
          this.followed = false;
        })
      }
    })
  }

}
