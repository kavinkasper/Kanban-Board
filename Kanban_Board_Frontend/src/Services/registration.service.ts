import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { KANBAN } from 'src/Models/Kanban';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  url="http://localhost:9000/api/v2/register"

  constructor(private HTTP:HttpClient) { }

  postregisteration(form:KANBAN):Observable<KANBAN>{
    return this.HTTP.post<KANBAN>(`${this.url}`,form);
  }
}
