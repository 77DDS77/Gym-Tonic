import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { faDumbbell, faLayerGroup, faPlus, faScroll, faDoorOpen } from '@fortawesome/free-solid-svg-icons';
import { AuthService } from 'src/app/Services/auth.service';


@Component({
  selector: 'app-navfoot',
  templateUrl: './navfoot.component.html',
  styleUrls: ['./navfoot.component.scss']
})
export class NavfootComponent implements OnInit {

  exerciseUp:boolean = false;
  workoutUp:boolean = false;
  planUp:boolean = false;
  logoutUp:boolean = false;

  faPlus = faPlus;
  faDumbbell = faDumbbell;
  faScroll = faScroll;
  faLayerGroup = faLayerGroup;
  faDoorOpen = faDoorOpen;

  constructor(
    private auth:AuthService,
    private router:Router
    ) { }

  ngOnInit(): void {
  }

  triggerActions(){
    this.exerciseUp = !this.exerciseUp;
    setTimeout(() => {
      this.workoutUp = !this.workoutUp;
    },100)
    setTimeout(() => {
      this.planUp = !this.planUp;
    },200)
    setTimeout(() => {
      this.logoutUp = !this.logoutUp;
    },300)
  }

  logout(){
    this.auth.logOut()
    this.router.navigate(['/'])
  }

}
