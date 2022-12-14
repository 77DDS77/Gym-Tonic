import { Component, OnInit } from '@angular/core';
import { JwtUser } from 'src/app/Models/jwt-user';
import { AuthService } from 'src/app/Services/auth.service';
import { UserService } from 'src/app/Services/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  user!:JwtUser | undefined;

  profileEditing:boolean = false;
  pswrdEditing:boolean = false;

  constructor(
    private auth:AuthService,
    private userSvc:UserService
  ) { }

  ngOnInit(): void {
    this.userSvc.getById(this.auth.getLoggedUser().id)
    .subscribe({
      next: (user) => this.user = user,
      error: (err) => console.error(err)
    })
  }

  editProfile() {
    this.profileEditing = !this.profileEditing;
    this.pswrdEditing = false;
  }
  editPswrd() {
    this.pswrdEditing = !this.pswrdEditing;
    this.profileEditing = false;
  }

}
