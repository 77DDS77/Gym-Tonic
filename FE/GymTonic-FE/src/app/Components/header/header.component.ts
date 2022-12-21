import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { JwtUser } from 'src/app/Models/jwt-user';
import { AuthService } from 'src/app/Services/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  user!: JwtUser;

  constructor(
    private auth:AuthService,
    private router:Router) { }

  ngOnInit(): void {
    this.getLoggedUserData();
  }

  redirect(){
    let userRole = this.auth.getLoggedUser().roles
    if(userRole.includes("ROLE_GTUSER")){
      this.router.navigate(['/user-home'])
    }else if(userRole.includes("ROLE_GTPERSONALTRAINER")){
      this.router.navigate(['/pt-home'])
    }
  }

  isUserLogged(){
    return this.auth.isUserLogged();
  }

  getLoggedUserData(){
    console.log(this.isUserLogged());

    if(this.isUserLogged()){
      this.user = this.auth.getLoggedUser();
    }
  }

}
