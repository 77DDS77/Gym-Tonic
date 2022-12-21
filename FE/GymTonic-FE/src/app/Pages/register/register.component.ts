import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { GTPTrainer } from 'src/app/Models/gtptrainer';
import { GTUser } from 'src/app/Models/gtuser';
import { JwtUser } from 'src/app/Models/jwt-user';
import { AuthService } from 'src/app/Services/auth.service';
import { UserService } from 'src/app/Services/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  form!: FormGroup;
  isFormValid!: boolean;
  uniqueData:boolean = true;
  matchingPsw:boolean = true;

  constructor(
    private authSrv: AuthService,
    private userSrv: UserService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.form = new FormGroup({
      name: new FormControl(null, Validators.required),
      lastname: new FormControl(null, Validators.required),
      username: new FormControl(null, Validators.required),
      email: new FormControl(null, Validators.required),
      password: new FormControl(null, [
        Validators.required,
        Validators.minLength(5),
      ]),
      userType: new FormControl(null, Validators.required)
    });
  }

  // checkValidity(input:string):boolean{
  //   return (this.form.get(input)?.invalid
  //     && this.form.get(input)?.touched
  //     && this.form.get(input)?.dirty) || false
  // }

  submit() {
    if(this.form.valid){
      if(this.form.value.userType == 'athlete'){
        this.userSrv.newGTUser(new GTUser(
          this.form.value.username,
          this.form.value.email,
          this.form.value.name,
          this.form.value.lastname,
          this.form.value.password
        ))
        .subscribe({
          complete: () => {
            console.log("utente creato")
            let user = {
              username:this.form.value.username,
              password:this.form.value.password
            }
            this.authSrv.login(user)
            .subscribe({
              next: (authRes) => this.authSrv.saveAccessData(authRes),
              complete: () =>{
                this.router.navigate(['/user-home']);
              }
            })
            this.uniqueData = true;
            this.form.reset();
          },
          error: (err) => {
            console.error(err.error, err.message);
            this.uniqueData = false;
          }
        })
      }else if(this.form.value.userType == 'personalTrainer'){
        this.userSrv.newGTPT(new GTPTrainer(
          this.form.value.username,
          this.form.value.email,
          this.form.value.name,
          this.form.value.lastname,
          this.form.value.password
        ))
        .subscribe({
          complete: () => {
            console.log("personal trainer creato")
            let user = {
              username:this.form.value.username,
              password:this.form.value.password
            }
            this.authSrv.login(user)
            .subscribe({
              complete: () =>{
                this.router.navigate(['/pt-home']);
              }
            })
            this.uniqueData = true;
            this.form.reset();
          },
          error: (err) => {
            console.log(err.error, err.message);
            this.uniqueData = false;
          }
        })
      }

    }
  }
}
