import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { SwalComponent } from '@sweetalert2/ngx-sweetalert2';
import { AuthService } from 'src/app/Services/auth.service';
import Swal from 'sweetalert2';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  form!: FormGroup;
  formIsValid!: boolean;
  deleteExerciseSwal!:SwalComponent

  constructor(
    private auth:AuthService,
    private router:Router
    ) { }

  ngOnInit(): void {
    this.form = new FormGroup({
      username: new FormControl(null, Validators.required),
      password: new FormControl(null, Validators.required),
    })
  }

  login():void{
    if(this.form.valid){

      this.auth.login(this.form.value)
      .subscribe( {
        next: (res) => {
          this.auth.saveAccessData(res);
          if(res.roles.includes("ROLE_GTPERSONALTRAINER")){
            this.router.navigate(['/pt-home']);
          }else{
            this.router.navigate(['/user-home']);
          }
          this.form.reset();
        },
        error: () => {
          Swal.fire({
            title: 'Error!',
            text: 'Invalid credentials!',
            icon: 'error',
            showConfirmButton: false,
            timer: 2000,
            timerProgressBar: true
          })
          this.form.reset();
        }
      })

    }
    else{
      this.formIsValid = false;
    }
  }

}
