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
    return this.http.delete(apiUrl + '/' + userId + '/delete-plan/' + planId, {responseType:'text'});
  }

  postNewPlan(userId:number, plan:Plan):Observable<Plan>{
    return this.http.post<Plan>(apiUrl + '/' + userId + '/new-plan', plan);
  }
}
