import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { faDumbbell, faLayerGroup, faPlus, faScroll, faDoorOpen } from '@fortawesome/free-solid-svg-icons';
import { AuthService } from 'src/app/Services/auth.service';
import { NgbModal, NgbModalConfig } from '@ng-bootstrap/ng-bootstrap';



@Component({
  selector: 'app-navfoot',
  templateUrl: './navfoot.component.html',
  styleUrls: ['./navfoot.component.scss']
})
export class NavfootComponent implements OnInit {

  exerciseUp:boolean = true;
  workoutUp:boolean = true;
  planUp:boolean = true;
  logoutUp:boolean = true;
  actionsUp:boolean = false;

  faPlus = faPlus;
  faDumbbell = faDumbbell;
  faScroll = faScroll;
  faLayerGroup = faLayerGroup;
  faDoorOpen = faDoorOpen;

  constructor(
    private auth:AuthService,
    private router:Router,
    private modalService: NgbModal,
    private x:NgbModalConfig
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

  openModale(content: any) {
    this.x.backdropClass = 'my-modal-backdrop';
    this.x.windowClass = 'my-modal-window'
    this.x.centered = true
		this.modalService.open(content, this.x);
	}

}
