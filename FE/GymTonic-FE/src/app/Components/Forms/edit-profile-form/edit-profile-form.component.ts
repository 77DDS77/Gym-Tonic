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
import { JwtUser } from 'src/app/Models/jwt-user';
import { AuthService } from 'src/app/Services/auth.service';
import { UserService } from 'src/app/Services/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-edit-profile-form',
  templateUrl: './edit-profile-form.component.html',
  styleUrls: ['./edit-profile-form.component.scss'],
})
export class EditProfileFormComponent implements OnInit {
  @Input() user!: JwtUser | undefined;
  @Output() updated = new EventEmitter<JwtUser>();

  profileForm!: FormGroup;

  constructor(
    private userSvc: UserService,
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
        currentUser.name = this.profileForm.value.name;
        currentUser.lastname = this.profileForm.value.lastname;
        currentUser.username = this.profileForm.value.username;
        currentUser.email = this.profileForm.value.email;

        this.userSvc.updateGTUser(currentUser).subscribe({
          next: () => {
            Swal.fire({
              icon: 'success',
              title: 'Updated successfully!',
              timer: 1500,
              timerProgressBar: true,
              showConfirmButton: false,
              confirmButtonColor: '#FF7517'
            });
          },
          complete: () => {
            this.auth.logOut();
            this.router.navigate(['']);
          },
          error: (error) => {
            console.error(error);
            if (error.error.status === 500) {
              Swal.fire({
                icon: 'error',
                title: 'ERROR!',
                text: 'Username is already taken! Please try again.',
                confirmButtonColor: '#FF7517'
              });
            }
          },
        });
      } else if (result.isDenied) {
        Swal.fire({
          icon: 'info',
          title:'Changes are not saved',
          timer: 1500,
          timerProgressBar: true,
          showConfirmButton: false,
        });
      }
    });
  }

}
