import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { apiUrl } from 'src/environments/environment';
import { GTUser } from '../Models/gtuser';
import { SearchedUser } from '../Models/searchedUser';

@Injectable({
  providedIn: 'root'
})
export class PtService {

  constructor(private http:HttpClient) { }

  searchUser(username:string):Observable<SearchedUser[]>{
    return this.http.get<SearchedUser[]>(apiUrl + '/p-trainers/search-user/' + username);
  }
}
