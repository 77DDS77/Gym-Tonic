import { Component, Input, OnInit } from '@angular/core';
import { faDumbbell, faScroll, faLayerGroup } from '@fortawesome/free-solid-svg-icons';
import { SearchedUser } from 'src/app/Models/searchedUser';

@Component({
  selector: 'manage-user',
  templateUrl: './manage-user.component.html',
  styleUrls: ['./manage-user.component.scss']
})
export class ManageUserComponent implements OnInit {

  @Input() user!:SearchedUser;

  exercise = faDumbbell;
  workout = faScroll;
  plan = faLayerGroup;

  constructor() { }

  ngOnInit(): void {
    console.log(this.user);
  }

}
