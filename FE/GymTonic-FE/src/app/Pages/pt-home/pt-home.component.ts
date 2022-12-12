import { Component, OnInit } from '@angular/core';
import { GTUser } from 'src/app/Models/gtuser';
import { SearchedUser } from 'src/app/Models/searchedUser';
import { PtService } from 'src/app/Services/pt.service';

@Component({
  selector: 'app-pt-home',
  templateUrl: './pt-home.component.html',
  styleUrls: ['./pt-home.component.scss']
})
export class PtHomeComponent implements OnInit {

  usersFound:SearchedUser[] = [];
  searchBar!:HTMLInputElement
  username:string = "";

  constructor(private ptSvc:PtService) { }

  ngOnInit(): void {
  }


  searchUser(){
    console.log(this.username);

    this.ptSvc.searchUser(this.username)
    .subscribe(users => {
      this.usersFound = users;
      this.username = "";
    })
  }

}
