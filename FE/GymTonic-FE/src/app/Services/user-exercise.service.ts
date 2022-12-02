import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { apiUrl } from 'src/environments/environment';
import { UserExercise } from '../Models/user-exercise';

@Injectable({
  providedIn: 'root'
})
export class UserExerciseService {

  constructor(private http:HttpClient) { }

  getUserExercises(userId:number):Observable<UserExercise[]>{
    return this.http.get<UserExercise[]>(apiUrl + '/' +userId + '/exercises');
  }

  postNewUserExercise(userId:number, exercise:UserExercise):Observable<UserExercise>{
    return this.http.post<UserExercise>(apiUrl + '/' + userId + '/new-exercise', exercise);
  }
}
