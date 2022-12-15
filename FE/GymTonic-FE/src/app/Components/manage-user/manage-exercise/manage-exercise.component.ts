import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { faSave, faTrashAlt } from '@fortawesome/free-solid-svg-icons';
import { UserExercise } from 'src/app/Models/user-exercise';
import { UserExerciseService } from 'src/app/Services/user-exercise.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'manage-exercise',
  templateUrl: './manage-exercise.component.html',
  styleUrls: ['./manage-exercise.component.scss']
})
export class ManageExerciseComponent implements OnInit {

  @Input() exercise!: UserExercise
  @Input() userId!:number;

  @Output() exDeleted = new EventEmitter<boolean>();

  updateExercise!:FormGroup;

  save = faSave;
  trash = faTrashAlt;

  constructor(
    private exSvc:UserExerciseService
    ) { }

  ngOnInit(): void {
    this.updateExercise = new FormGroup({
      reps: new FormControl(this.exercise.reps, Validators.required),
      series: new FormControl(this.exercise.series, Validators.required)
    })
  }


  updateEx(){
    this.exercise.reps = this.updateExercise.value.reps;
    this.exercise.reps = this.updateExercise.value.series;
    this.exSvc.updateUserExercise(this.userId, this.exercise)
    .subscribe(updated => {
      this.exercise = updated;
      Swal.fire({
        title: 'Exercise updated!',
        icon: 'success',
        showConfirmButton: false,
        timer: 1500,
        timerProgressBar: true,
      })
    })
  }

  deleteEx(){
    Swal.fire({
      title: 'Are you sure?',
      text: 'This action is not reversible.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, delete it!'
    }).then(result => {
      if(result.isConfirmed){
        this.exSvc.deleteUserExercise(this.userId, this.exercise.id)
        .subscribe(() => {
          this.exDeleted.emit(true)
          Swal.fire({
            title: 'Exercise deleted!',
            icon: 'success',
            showConfirmButton: false,
            timer: 1500,
            timerProgressBar: true,
          })
        })
      }
    })


  }
}
