import { Component, Input, OnInit } from '@angular/core';
import { AsyncValidatorFn, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { JwtUser } from 'src/app/Models/jwt-user';
import { UserService } from 'src/app/Services/user.service';

@Component({
  selector: 'app-edit-password-form',
  templateUrl: './edit-password-form.component.html',
  styleUrls: ['./edit-password-form.component.scss']
})
export class EditPasswordFormComponent implements OnInit {

  @Input() user!:JwtUser|undefined;

  editPassword!:FormGroup

  constructor(
    private userSvc:UserService
  ) { }

  ngOnInit(): void {
    this.editPassword = new FormGroup({
      oldPassword: new FormControl('', {
        validators: [Validators.required]
      }),
      newPassword: new FormControl('', Validators.required),
      confirmPassword: new FormControl('', Validators.required)
    });
  }

  updatePassword(){

    if(this.user){
      const USER = this.user;

      this.userSvc.checkPassword(USER.id, this.editPassword.value.oldPassword)
      .subscribe({
        next: (res) => {
          if (res) {
            if(this.editPassword.value.newPassword === this.editPassword.value.confirmPassword){

              this.userSvc.updatePassword(USER.id, this.editPassword.value.newPassword)
              .subscribe({
                next: () => {
                  console.log('password updated');
                },
                error: (err) => {
                  console.error('password not updated');
                  console.error(err);
                },
              });
            }else{
              console.error("new and confirm dont match");

            }
          } else {
            console.error("password old and actual don't match");
          }
        },
        error: (err) => {
          console.error(err);
        },
      });
    }
  }

  /*
  Async validator I can't make it work.

  checkOldPassword(control: FormControl): Promise<ValidationErrors | null> {
    return new Promise((resolve, reject) => {
      this.userSvc.checkPassword(634, control.value)
      .subscribe({
        next: res => {
          if(res) {
            resolve(null);
          } else {
            resolve({checkOldPassword: true});
          }
        },
        error: err => {
          console.error(err);
          reject({checkOldPassword: true});
        }
      });
    });
  }*/

}
