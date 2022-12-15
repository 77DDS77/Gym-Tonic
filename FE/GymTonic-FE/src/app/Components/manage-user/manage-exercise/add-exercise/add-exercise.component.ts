import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Exercise } from 'src/app/Models/exercise';
import { UserExercise } from 'src/app/Models/user-exercise';
import { ExerciseService } from 'src/app/Services/exercise.service';
import { UserExerciseService } from 'src/app/Services/user-exercise.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'add-exercise',
  templateUrl: './add-exercise.component.html',
  styleUrls: ['./add-exercise.component.scss']
})
export class AddExerciseComponent implements OnInit {

  @Input() userId!: number;

  @Output() outputExercise = new EventEmitter<UserExercise>();

  addExForm!: FormGroup;

  muscles: string[] =
    [
      'abdominals', 'abductors', 'adductors','biceps', 'calves', 'chest',
      'forearms', 'glutes', 'hamstrings', 'lats', 'lower_back', 'middle_back',
      'neck', 'quadriceps', 'traps', 'triceps'
    ];

  exercises: Exercise[] = []

  constructor(
    private exSvc: ExerciseService,
    private userExSvc: UserExerciseService
  ) { }

  ngOnInit(): void {
    this.addExForm = new FormGroup({
      muscle: new FormControl(null, Validators.required),
      exercise: new FormControl(null, Validators.required),
      reps: new FormControl(null, Validators.required),
      series: new FormControl(null, Validators.required)
    })
    this.addExForm.controls['exercise'].disable();
  }

  getExercise(){
    this.addExForm.controls['exercise'].reset();
    if(this.addExForm.value.muscle != null){
      this.exSvc.getExerciseByMuscle(this.addExForm.value.muscle)
      .subscribe(exs => {
        this.exercises = exs
        if(this.exercises.length > 0){this.addExForm.controls['exercise'].enable();}
      })
    }
    this.addExForm.controls['exercise'].disable();
  }

  postExercise(){

    if(this.addExForm.value.muscle != null ||
      this.addExForm.value.exercise != null ||
      this.addExForm.value.reps != null ||
      this.addExForm.value.series != null)
    {
      this.userExSvc.postNewUserExercise(this.userId, new UserExercise(
        this.addExForm.value.exercise, this.addExForm.value.reps, this.addExForm.value.series
      ))
      .subscribe(data => {
        this.outputExercise.emit(data)
        this.addExForm.reset();
        Swal.fire({
          title: 'Exercise added!',
          showConfirmButton:false,
          timer: 1000
        })
      })
    }
  }

}
