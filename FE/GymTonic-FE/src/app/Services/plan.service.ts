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

  deletePlan(userId:number, planId:number):Observable<string>{
    return this.http.post<string>(apiUrl + '/' + userId + '/delete-plan/' + planId, null);
  }
}
