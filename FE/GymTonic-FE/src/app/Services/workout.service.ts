import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { apiUrl } from 'src/environments/environment';
import { Workout } from '../Models/workout';

@Injectable({
  providedIn: 'root'
})
export class WorkoutService {

  constructor(private http:HttpClient) { }

  getUserWorkouts(userId:number):Observable<Workout[]>{
    return this.http.get<Workout[]>(apiUrl + '/' + userId + '/workouts');
  }

  postNewWorkout(userId:number, workout:Workout):Observable<Workout>{
    return this.http.post<Workout>(apiUrl + '/' + userId + '/new-workout', workout);
  }
}
