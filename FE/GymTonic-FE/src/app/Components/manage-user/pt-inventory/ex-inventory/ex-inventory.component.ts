import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { faTimes } from '@fortawesome/free-solid-svg-icons';
import { JwtUser } from 'src/app/Models/jwt-user';
import { UserExercise } from 'src/app/Models/user-exercise';
import { AuthService } from 'src/app/Services/auth.service';
import { UserExerciseService } from 'src/app/Services/user-exercise.service';
import { UserService } from 'src/app/Services/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'ex-inventory',
  templateUrl: './ex-inventory.component.html',
  styleUrls: ['./ex-inventory.component.scss'],
})
export class ExInventoryComponent implements OnInit {
  @Input() userId!: number;

  @Output() exAdded = new EventEmitter<UserExercise>();

  exInventory: UserExercise[] = [];

  times = faTimes;

  holdUser!:JwtUser;

  constructor(
    private authSvc: AuthService,
    private uexSvc: UserExerciseService,
  ) {}

  ngOnInit(): void {
    this.getExercises();
  }

  getExercises() {
    var userId = this.authSvc.getLoggedUser().id;
    this.uexSvc
      .getUserExercises(userId)
      .subscribe((exx) => (this.exInventory = exx));
  }

  addExToUser(ex: UserExercise) {
    let newEx = new UserExercise(ex.exercise, ex.reps, ex.series);
    this.uexSvc.postNewUserExercise(this.userId, newEx)
    .subscribe((ex) => {
      this.exAdded.emit(ex);
    });
  }
}
