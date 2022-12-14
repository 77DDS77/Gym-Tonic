import { Component, OnInit } from '@angular/core';
import { SearchedUser } from 'src/app/Models/searchedUser';
import { AuthService } from 'src/app/Services/auth.service';
import { PtService } from 'src/app/Services/pt.service';

@Component({
  selector: 'app-pt-home',
  templateUrl: './pt-home.component.html',
  styleUrls: ['./pt-home.component.scss']
})
export class PtHomeComponent implements OnInit {

  usersFound:SearchedUser[] = [];
  myUsers:SearchedUser[] = [];
  searchBar!:HTMLInputElement
  username:string = "";

  constructor(
    private ptSvc:PtService,
    private auth:AuthService
    ) { }

  ngOnInit(): void {
    this.getMyUsers()
  }

  getMyUsers(){
    this.ptSvc.getFollowed(this.auth.getLoggedUser().id)
    .subscribe(users => {
      this.myUsers = users;
    })
  }

  addFollowed(followed:boolean, userToAdd:SearchedUser){
    if(followed == true){
      this.myUsers.push(userToAdd);
    }else{
      const index = this.myUsers.indexOf(userToAdd, 0);
      this.myUsers.splice(index, 1);
    }
  }

  searchUser(){
    this.ptSvc.searchUser(this.username)
    .subscribe(users => {
      this.usersFound = users;
      this.username = "";
    })
  }

  clearSearch(){
    this.usersFound = [];
  }

}
