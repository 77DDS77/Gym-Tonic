import { Component, EventEmitter, Input, OnInit, Output, SimpleChanges } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { JwtUser } from 'src/app/Models/jwt-user';
import { AuthService } from 'src/app/Services/auth.service';
import { UserService } from 'src/app/Services/user.service';

@Component({
  selector: 'app-edit-profile-form',
  templateUrl: './edit-profile-form.component.html',
  styleUrls: ['./edit-profile-form.component.scss']
})
export class EditProfileFormComponent implements OnInit {

  @Input() user!:JwtUser | undefined;
  @Output() updated = new EventEmitter<JwtUser>();

  profileForm!:FormGroup;

  constructor(
    private userSvc: UserService,
    private auth: AuthService
  ) { }

  ngOnInit(): void {
  }

  /*
  Wait for the user to be fetched in the parent component
  and then writes the values in the edit profile form
  */
  ngOnChanges(changes: SimpleChanges) {
    if (changes['user']) {
      this.user = changes['user'].currentValue;
      this.profileForm = new FormGroup({
        name: new FormControl(this.user?.name, Validators.required),
        lastname: new FormControl(this.user?.lastname, Validators.required),
        username: new FormControl(this.user?.username, Validators.required),
        email: new FormControl(this.user?.email, Validators.required)
      });
    }
  }

  editProfile(){
    if(this.user != undefined){
      if(this.checkIfDataChanged()){
        this.user.name = this.profileForm.value.name;
        this.user.lastname = this.profileForm.value.lastname;
        this.user.username = this.profileForm.value.username;
        this.user.email = this.profileForm.value.email;

        this.userSvc.updateGTUser(this.user)
        .subscribe(updtd => {
          console.log(updtd);
          this.updated.emit(updtd);
          //manage error if username is already taken
        })

      }
    }

  }

  checkIfDataChanged(){
    if(
      this.profileForm.value.name === this.user?.name
      && this.profileForm.value.lastname === this.user?.lastname
      && this.profileForm.value.username === this.user?.username
      && this.profileForm.value.email === this.user?.email
      ){
        return false;
    }
    return true;
  }

}
