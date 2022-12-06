import { Component, Input, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { faPencilAlt, faTrash } from '@fortawesome/free-solid-svg-icons';
import { UserExercise } from 'src/app/Models/user-exercise';
import { AuthService } from 'src/app/Services/auth.service';
import { UserExerciseService } from 'src/app/Services/user-exercise.service';

@Component({
  selector: 'app-data-exercise',
  templateUrl: './data-exercise.component.html',
  styleUrls: ['./data-exercise.component.scss']
})
export class DataExerciseComponent implements OnInit {

  @Input() ex!:UserExercise;

  updateExercise!:FormGroup
  updating: boolean = false;
  deleted:boolean = false;

  faEdit = faPencilAlt
  faTrash = faTrash;

  constructor(
    private exSvc:UserExerciseService,
    private auth:AuthService,
    private router:Router) { }

  ngOnInit(): void {
    this.updateExercise = new FormGroup({
      reps: new FormControl(this.ex.reps, Validators.required),
      series: new FormControl(this.ex.series, Validators.required)
    })
  }

  updateEx(ex:UserExercise){
    const userId = this.auth.getLoggedUser().id;
    ex.reps = this.updateExercise.value.reps;
    ex.series = this.updateExercise.value.series;
    this.exSvc.updateUserExercise(userId, ex)
    .subscribe(res => {
      this.ex = res;
      this.updating = false;
    });
  }

  deleteEx(exId:number){
    const userId = this.auth.getLoggedUser().id;
    this.exSvc.deleteUserExercise(userId, exId)
    .subscribe(() => {
      console.log("deleted");
      this.deleted = true;
    });
  }
}
