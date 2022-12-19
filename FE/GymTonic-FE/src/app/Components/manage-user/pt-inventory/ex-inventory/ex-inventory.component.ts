import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { faTimes } from '@fortawesome/free-solid-svg-icons';
import { UserExercise } from 'src/app/Models/user-exercise';
import { AuthService } from 'src/app/Services/auth.service';
import { UserExerciseService } from 'src/app/Services/user-exercise.service';
import { UserService } from 'src/app/Services/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'ex-inventory',
  templateUrl: './ex-inventory.component.html',
  styleUrls: ['./ex-inventory.component.scss']
})
export class ExInventoryComponent implements OnInit {

  @Input() userId!:number;

  exInventory: UserExercise[] = [];
  exToAdd: UserExercise[] = [];

  times = faTimes;

  constructor(
    private authSvc:AuthService,
    private uexSvc: UserExerciseService,
    private userSvc:UserService
    ) { }

  ngOnInit(): void {
    this.getExercises();
  }

  getExercises(){
    var userId = this.authSvc.getLoggedUser().id;
    this.uexSvc.getUserExercises(userId)
    .subscribe(exx => this.exInventory = exx)
  }

  preAddOrRemove(ex:UserExercise){
    if(this.exToAdd.includes(ex)){
      const index = this.exToAdd.indexOf(ex);
      this.exToAdd.splice(index, 1);
      return false;
    }else{
      this.exToAdd.push(ex);
      return true;
    }
  }

  addedOrNot(ex:UserExercise){
    return this.exToAdd.includes(ex);
  }

  addExToUser(){
    this.userSvc.getById(this.userId)
    .subscribe(user => {
      console.log("PRE");
      console.log(user.userExercisesId);
      for(let ex of this.exToAdd){

        this.uexSvc.postNewUserExercise(this.userId, new UserExercise(ex.exercise, ex.reps, ex.series))
        .subscribe(() => {})
      }


      this.userSvc.updateGTUser(user)
      .subscribe(user => {
        console.log("POST");
        console.log(user.userExercisesId);

        Swal.fire({
          title: 'Added!',
          showConfirmButton: false,
          timer: 1500,
          timerProgressBar: true
        })
        this.exToAdd = [];
      })
    })
  }

}
