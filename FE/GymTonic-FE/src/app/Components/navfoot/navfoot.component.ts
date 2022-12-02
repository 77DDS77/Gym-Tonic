import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { faDumbbell, faLayerGroup, faPlus, faScroll, faDoorOpen, faMinus } from '@fortawesome/free-solid-svg-icons';
import { AuthService } from 'src/app/Services/auth.service';
import { NgbModal, NgbModalConfig } from '@ng-bootstrap/ng-bootstrap';

import Swal from 'sweetalert2/dist/sweetalert2.js';



@Component({
  selector: 'app-navfoot',
  templateUrl: './navfoot.component.html',
  styleUrls: ['./navfoot.component.scss']
})
export class NavfootComponent implements OnInit {


  actionsUp:boolean = false;

  faPlus = faPlus;
  faMinus = faMinus;
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
    const actions = document.getElementById('action-box');
    if(this.actionsUp == false){
      actions?.classList.add('pop-up')
      this.actionsUp = true;
    }else{
      actions?.classList.remove('pop-up')
      this.actionsUp = false;
    }
  }

  logout(){
    this.auth.logOut()
    this.router.navigate(['/'])
  }

}
