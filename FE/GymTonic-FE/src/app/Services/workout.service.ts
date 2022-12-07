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

  updateWorkout(userId:number, workout:Workout):Observable<Workout>{
    return this.http.put<Workout>(apiUrl + '/' + userId + '/update-workout', workout);
  }

  deleteWorkout(userId:number, workoutId:number):Observable<string>{
    return this.http.delete(apiUrl + '/' + userId + '/delete-workout/' + workoutId, {responseType:'text'});
  }
}
