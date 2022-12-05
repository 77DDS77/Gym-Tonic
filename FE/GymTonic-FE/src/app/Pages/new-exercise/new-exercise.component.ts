import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Exercise } from 'src/app/Models/exercise';
import { UserExercise } from 'src/app/Models/user-exercise';
import { AuthService } from 'src/app/Services/auth.service';
import { ExerciseService } from 'src/app/Services/exercise.service';
import { UserExerciseService } from 'src/app/Services/user-exercise.service';

@Component({
  selector: 'app-new-exercise',
  templateUrl: './new-exercise.component.html',
  styleUrls: ['./new-exercise.component.scss']
})
export class NewExerciseComponent implements OnInit {


  constructor(
    ) { }

  ngOnInit(): void {
  }

}
