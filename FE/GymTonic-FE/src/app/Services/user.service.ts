import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { apiUrl } from 'src/environments/environment';
import { GTPTrainer } from '../Models/gtptrainer';
import { GTUser } from '../Models/gtuser';
import { JwtUser } from '../Models/jwt-user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http:HttpClient) { }

  getAllUsers():Observable<JwtUser[]>{
    return this.http.get<JwtUser[]>(apiUrl + '/all-users');
  }

  getById(id:number):Observable<JwtUser>{
    return this.http.get<JwtUser>(apiUrl + '/' + id);
  }

  newGTUser(user:GTUser):Observable<GTUser>{
    return this.http.post<GTUser>(apiUrl + '/users/new-user', user);
  }

  newGTPT(pt:GTPTrainer):Observable<GTPTrainer>{
    return this.http.post<GTPTrainer>(apiUrl + '/p-trainers/new-pt', pt);
  }

  updateGTUser(user:GTUser):Observable<GTUser>{
    return this.http.put<GTUser>(apiUrl + '/users/update-user', user);
  }

  checkPassword(userId:number, inputOldPw:string):Observable<boolean>{
    return this.http.post<boolean>(apiUrl + '/check-password/' + userId, inputOldPw);
  }

  updatePassword(userId:number, newPassword:string):Observable<JwtUser>{
    return this.http.put<JwtUser>(apiUrl + '/update-password/' + userId, newPassword);
  }
}
