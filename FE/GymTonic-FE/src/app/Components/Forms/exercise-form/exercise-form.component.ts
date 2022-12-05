import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Exercise } from 'src/app/Models/exercise';
import { UserExercise } from 'src/app/Models/user-exercise';
import { AuthService } from 'src/app/Services/auth.service';
import { ExerciseService } from 'src/app/Services/exercise.service';
import { UserExerciseService } from 'src/app/Services/user-exercise.service';

@Component({
  selector: 'app-exercise-form',
  templateUrl: './exercise-form.component.html',
  styleUrls: ['./exercise-form.component.scss']
})
export class ExerciseFormComponent implements OnInit {

  form!:FormGroup
  formIsValid: boolean = true;
  exercises: Exercise[] = [];

  muscles: string[] =
    [
      'abdominals', 'abductors', 'adductors','biceps', 'calves', 'chest',
      'forearms', 'glutes', 'hamstrings', 'lats', 'lower_back', 'middle_back',
      'neck', 'quadriceps', 'traps', 'triceps'
    ];

    constructor(
      private auth:AuthService,
      private exSvc:ExerciseService,
      private userExSrc: UserExerciseService
      ) { }

    ngOnInit(): void {


      this.form = new FormGroup({
        muscle: new FormControl(null, Validators.required),
        exercise: new FormControl(null, Validators.required),
        reps: new FormControl(null, Validators.required),
        series: new FormControl(null, Validators.required)
      })
      this.form.controls['exercise'].disable();
    }

  getExercise(){
    this.form.controls['exercise'].reset();
    if(this.form.value.muscle != null){
      this.exSvc.getExerciseByMuscle(this.form.value.muscle)
      .subscribe(exs => {
        this.exercises = exs
        if(this.exercises.length > 0){this.form.controls['exercise'].enable();}
      })
    }
    this.form.controls['exercise'].disable();
  }

  postExercise(){
    let userId:number = this.auth.getLoggedUser().id;

    if(this.form.value.muscle != null ||
      this.form.value.exercise != null ||
      this.form.value.reps != null ||
      this.form.value.series != null)
    {
      this.userExSrc.postNewUserExercise(userId, new UserExercise(
        this.form.value.exercise, this.form.value.reps, this.form.value.series
      ))
      .subscribe(data => {
        console.log(data);
        this.form.reset();
      })
    }
    //feedback utente form non valido
  }

}
