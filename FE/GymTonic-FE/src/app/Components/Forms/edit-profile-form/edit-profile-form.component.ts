import {
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
  SimpleChanges,
} from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { GTPTrainer } from 'src/app/Models/gtptrainer';
import { JwtUser } from 'src/app/Models/jwt-user';
import { AuthService } from 'src/app/Services/auth.service';
import { PtService } from 'src/app/Services/pt.service';
import { UserService } from 'src/app/Services/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-edit-profile-form',
  templateUrl: './edit-profile-form.component.html',
  styleUrls: ['./edit-profile-form.component.scss'],
})
export class EditProfileFormComponent implements OnInit {
  @Input() user!: JwtUser | undefined;

  profileForm!: FormGroup;

  constructor(
    private userSvc: UserService,
    private ptSvc: PtService,
    private auth: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {}

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
        email: new FormControl(this.user?.email, Validators.required),
      });
    }
  }

  editProfile() {
    if (this.user != undefined) {
      const HOLDUSER = this.user
      if (this.checkIfDataChanged()) {
        this.manageUpdate(HOLDUSER);
      }
    }
  }

  /*
    Checks if the user data has been changed or not.
  */
  protected checkIfDataChanged() {
    if (
      this.profileForm.value.name === this.user?.name &&
      this.profileForm.value.lastname === this.user?.lastname &&
      this.profileForm.value.username === this.user?.username &&
      this.profileForm.value.email === this.user?.email
    ) {
      return false;
    }
    return true;
  }

  /*
    Refactored for readability.
    Basically fires modals for UX and manage
    back-end side update.
  */
  private manageUpdate(currentUser:JwtUser){
    Swal.fire({
      icon: 'warning',
      title: 'Attention!',
      text: 'This action will log you out. Please log back in.',
      showDenyButton: true,
      showCancelButton: false,
      confirmButtonText: 'Confirm',
      confirmButtonColor: '#FF7517',
      denyButtonText: `Don't save`,
      denyButtonColor: '#3E3939'
    }).then((result) => {
      if (result.isConfirmed) {
        const LOGGED_ROLES = this.auth.getLoggedUser().roles
        currentUser.name = this.profileForm.value.name;
        currentUser.lastname = this.profileForm.value.lastname;
        currentUser.username = this.profileForm.value.username;
        currentUser.email = this.profileForm.value.email;

        /*
         * Manage both User and Personal Trainer update
        */
        if(LOGGED_ROLES.includes("ROLE_GTUSER")){
          //GTUSER
          this.gtUserUpdate(currentUser);
        }else if(LOGGED_ROLES.includes("ROLE_GTPERSONALTRAINER")){
          //GTPERSONALTRAINER
          this.personalTrainerUpdate(currentUser as GTPTrainer);
        }

      } else if (result.isDenied) {
        this.notSavedSwal();
      }
    });
  }

  /**
   * Update for GTUSER
  */
  gtUserUpdate(user:JwtUser){
    this.userSvc.updateGTUser(user).subscribe({
      next: () => {
        this.successUpdateSwal();
      },
      complete: () => {
        this.auth.logOut();
        this.router.navigate(['']);
      },
      error: (error) => {
        console.error(error);
        if (error.error.status === 500) {
          this.usernameTakenSwal();
        }
      }
    });
  }

  /**
   * Update for GTPERSONALTRAINER
  */
  personalTrainerUpdate(user:GTPTrainer){
    this.ptSvc.updatePT(user).subscribe({
      next: () => {
        this.successUpdateSwal();
      },
      complete: () => {
        this.auth.logOut();
        this.router.navigate(['']);
      },
      error: (error) => {
        console.error(error);
        if (error.error.status === 500) {
          this.usernameTakenSwal();
        }
      }
    });
  }

  // -- MODALS --
  successUpdateSwal(){
    Swal.fire({
      icon: 'success',
      title: 'Updated successfully!',
      timer: 1500,
      timerProgressBar: true,
      showConfirmButton: false,
      confirmButtonColor: '#FF7517'
    });
  }

  usernameTakenSwal(){
    Swal.fire({
      icon: 'error',
      title: 'ERROR!',
      text: 'Username is already taken! Please try again.',
      confirmButtonColor: '#FF7517'
    });
  }

  notSavedSwal(){
    Swal.fire({
      icon: 'info',
      title:'Changes are not saved',
      timer: 1500,
      timerProgressBar: true,
      showConfirmButton: false,
    });
  }

}
