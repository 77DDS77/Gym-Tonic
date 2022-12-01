import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { apiUrl } from 'src/environments/environment';
import { Plan } from '../Models/plan';

@Injectable({
  providedIn: 'root'
})
export class PlanService {

  constructor(private http:HttpClient) { }

  getUserPlans(userId:number):Observable<Plan[]>{
    return this.http.get<Plan[]>(apiUrl + '/' + userId + '/plans')
  }
}
