import { Component, OnInit } from '@angular/core';
import { faQuestionCircle } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-new-exercise',
  templateUrl: './new-exercise.component.html',
  styleUrls: ['./new-exercise.component.scss']
})
export class NewExerciseComponent implements OnInit {

  question = faQuestionCircle;

  constructor(
    ) { }

  ngOnInit(): void {
  }

}
