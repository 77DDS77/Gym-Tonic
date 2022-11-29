import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/Services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  form!: FormGroup;
  isFormValid!: boolean;

  constructor(
    private authSrv: AuthService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.form = new FormGroup({
      name: new FormControl(null, Validators.required),
      lastname: new FormControl(null, Validators.required),
      username: new FormControl(null, [Validators.required]),
      avatar: new FormControl(null, Validators.required),
      email: new FormControl(null,[ Validators.required]),
      password: new FormControl(null, [
        Validators.required,
        Validators.minLength(5),
      ]),
    });
  }

  checkValidity(input:string):boolean{
    return (this.form.get(input)?.invalid
      && this.form.get(input)?.touched
      && this.form.get(input)?.dirty) || false
  }

  //async username validator
  usernameValidator = (control:AbstractControl) => {
    // return new Promise<ValidationErrors | null>((resolve) => {
    //   this.userSrv.getAllUsers()
    //   .subscribe(users => {
    //     for (let user of users) {
    //       if (user.username == control.value) {
    //         resolve ({usernameIsTaken:true})
    //       }
    //     }
    //     resolve(null)
    //   })
    // })
  }

  //async email validator
  emailValidator = (control:AbstractControl) => {
    // return new Promise<ValidationErrors | null>((resolve) => {
    //   this.userSrv.getAllUsers()
    //   .subscribe(users => {
    //     for (let user of users) {
    //       if (user.email == control.value) {
    //         resolve ({emailIsTaken:true})
    //       }
    //     }
    //     resolve(null)
    //   })
    // })
  }

  submit() {

  }

}
