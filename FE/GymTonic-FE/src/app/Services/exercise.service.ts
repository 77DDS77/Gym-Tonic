import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { apiUrl } from 'src/environments/environment';
import { Exercise } from '../Models/exercise';

@Injectable({
  providedIn: 'root'
})
export class ExerciseService {

  constructor(private http:HttpClient) { }

  getExerciseByMuscle(muscle:string):Observable<Exercise[]>{
    return this.http.get<Exercise[]>(apiUrl + '/exercise/' + muscle);
  }
}
