import { Component, Input, OnInit } from '@angular/core';
import { faAngleDown, faAngleUp } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-ex-description',
  templateUrl: './ex-description.component.html',
  styleUrls: ['./ex-description.component.scss']
})
export class ExDescriptionComponent implements OnInit {

  @Input() description!:string;

  angleDown = faAngleDown;
  angleUp = faAngleUp;

  descriptionUp:boolean = false;

  constructor() { }

  ngOnInit(): void {
  }

}
