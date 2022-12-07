import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { faPencilAlt, faTrash } from '@fortawesome/free-solid-svg-icons';
import { Exercise } from 'src/app/Models/exercise';
import { UserExercise } from 'src/app/Models/user-exercise';
import { Workout } from 'src/app/Models/workout';
import { AuthService } from 'src/app/Services/auth.service';
import { ExerciseService } from 'src/app/Services/exercise.service';
import { UserExerciseService } from 'src/app/Services/user-exercise.service';
import { WorkoutService } from 'src/app/Services/workout.service';

@Component({
  selector: 'app-data-workout',
  templateUrl: './data-workout.component.html',
  styleUrls: ['./data-workout.component.scss']
})
export class DataWorkoutComponent implements OnInit {

  @Input() wrk!:Workout;
  @Output() exerciseAdded = new EventEmitter<UserExercise>();
  editWorkout!:FormGroup
  exerciseForm!:FormGroup

  workoutExercises: UserExercise[] = [];
  exercises: Exercise[] = [];
  muscles: string[] =
    [
      'abdominals', 'abductors', 'adductors','biceps', 'calves', 'chest',
      'forearms', 'glutes', 'hamstrings', 'lats', 'lower_back', 'middle_back',
      'neck', 'quadriceps', 'traps', 'triceps'
    ];

  deleted:boolean = false;
  updating:boolean = false;
  size!:number; //size of the original UserExercise array

  faEdit = faPencilAlt
  faTrash = faTrash;


  constructor(
    private wrkSvc:WorkoutService,
    private exSvc:ExerciseService,
    private usExSvc:UserExerciseService,
    private auth:AuthService
    ) { }

  ngOnInit(): void {

    this.size = this.wrk.userExercises.length;
    this.workoutExercises = this.wrk.userExercises;

    this.editWorkout = new FormGroup({
      name: new FormControl(this.wrk.name, Validators.required)
    })
    this.exerciseForm = new FormGroup({
      muscle: new FormControl(null, Validators.required),
      exercise: new FormControl(null, Validators.required),
      reps: new FormControl(null, Validators.required),
      series: new FormControl(null, Validators.required)
    })
  }

  removeExercise(ex:UserExercise){
    const index = this.workoutExercises.indexOf(ex, 0);
    this.workoutExercises.splice(index, 1);
  }

  canUpdate():boolean{
    let sameName:boolean = this.wrk.name == this.editWorkout.value.name;
    let sameExercise:boolean = this.size == this.workoutExercises.length;

    if(sameName==true && sameExercise==true){
      return false;
    }else{
      return true;
    }
  }

  updateWorkout(){
    let userId:number = this.auth.getLoggedUser().id;

    this.wrk.name = this.editWorkout.value.name;
    this.wrk.userExercises = this.workoutExercises
    this.wrkSvc.updateWorkout(userId, this.wrk)
    .subscribe( updtWrk => {
      this.wrk = updtWrk;
      this.exerciseForm.reset();
      this.updating = false;
    })
  }



  deleteWorkout(workoutId:number){
    let userId = this.auth.getLoggedUser().id;
    this.wrkSvc.deleteWorkout(userId, workoutId)
    .subscribe(() => {
      this.deleted = true;
    })

  }

  sendExerciseOutput(ex:UserExercise){
    this.exerciseAdded.emit(ex);
  }

  addExercise(){
    let userId:number = this.auth.getLoggedUser().id;

    if(this.exerciseForm.value.muscle != null ||
      this.exerciseForm.value.exercise != null ||
      this.exerciseForm.value.reps != null ||
      this.exerciseForm.value.series != null)
    {
      this.usExSvc.postNewUserExercise(userId, new UserExercise(
        this.exerciseForm.value.exercise, this.exerciseForm.value.reps, this.exerciseForm.value.series
      ))
      .subscribe((newExAdded) => {

        this.sendExerciseOutput(newExAdded);

        this.workoutExercises.push(newExAdded);
        this.exerciseForm.reset();
      })
    }
  }

  getExercise(){
    this.exerciseForm.controls['exercise'].reset();
    if(this.exerciseForm.value.muscle != null){
      this.exSvc.getExerciseByMuscle(this.exerciseForm.value.muscle)
      .subscribe(exs => {
        this.exercises = exs
        if(this.exercises.length > 0){this.exerciseForm.controls['exercise'].enable();}
      })
    }
    this.exerciseForm.controls['exercise'].disable();
  }

}
