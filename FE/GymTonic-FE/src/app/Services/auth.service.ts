import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { apiUrl } from 'src/environments/environment';

type AuthResponse = {
  token: string,
  id:number,
  username:string,
  roles:string[],
  data:Date
}

export interface ILogin {
  username: string,
  password: string
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http:HttpClient) { }

  login(loginData:ILogin){
    return this.http.post<AuthResponse>(apiUrl+'/login', loginData)
  }

  isUserLogged():boolean{
    return localStorage.getItem('access') != null
  }

  getLoggedUser(){
    let db = localStorage.getItem('access')
    return db ? JSON.parse(db).user : null
  }

  getAccessToken():string{
    let db = localStorage.getItem('access')
    return db ? JSON.parse(db).token : null
  }

  saveAccessData(data:AuthResponse){
    localStorage.setItem('access',JSON.stringify(data))
  }

  logOut(){
    localStorage.removeItem('access')
  }
}
