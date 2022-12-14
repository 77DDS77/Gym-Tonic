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

  updateUserExercise(userId:number, exercise:UserExercise):Observable<UserExercise>{
    return this.http.put<UserExercise>(apiUrl + '/' + userId + '/update-exercise', exercise);
  }

  deleteUserExercise(userId:number, exerciseId:number):Observable<string>{
    return this.http.delete(apiUrl + '/' + userId + '/delete-exercise' + '/' + exerciseId, {responseType:'text'});
  }
}
