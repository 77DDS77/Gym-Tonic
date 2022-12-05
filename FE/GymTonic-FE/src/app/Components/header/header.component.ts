import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/Services/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  constructor(
    private auth:AuthService,
    private router:Router) { }

  ngOnInit(): void {
  }

  redirect(){
    let userRole = this.auth.getLoggedUser().roles
    if(userRole.includes("ROLE_GTUSER")){
      this.router.navigate(['/user-home'])
    }else if(userRole.includes("ROLE_GTPERSONALTRAINER")){
      this.router.navigate(['/pt-home'])
    }
  }

}
