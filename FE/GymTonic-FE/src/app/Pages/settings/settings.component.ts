import { Component, OnInit } from '@angular/core';
import { faAddressCard, faSave } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.scss']
})
export class SettingsComponent implements OnInit {

  profile = faAddressCard;
  saved = faSave;

  constructor() { }

  ngOnInit(): void {
  }

}
